# Developer Guide

## Overview
This document will serve to help developers wanting to contribute to the project by covering things like app architecture, third party libraries used for major functions, as well as how the app interacts with the Elrond blockchain.

## Coding Standards
It's highly encouraged to follow the existing coding standards which has not been formerly defined, but the project pretty much follows the [SOLID Principles](https://www.freecodecamp.org/news/solid-principles-explained-in-plain-english/) as well as the DRY (Don't Repeat Yourself) pattern. Keeping this in mind will help keep the app maintainable and easy to follow for other developers.

## Architecture Overview
The architecture is typical of your standard Android project. It also employs the factory design pattern where most objects are managed by the IoC container. It uses [Koin](https://github.com/InsertKoinIO/koin) for dependency injection. 

## Development Environment
* Android Studio

## Project Structure
The project has two modules - <i>app</i> and <i>data</i>

The app module is typical of any standard Android project. It holds all files that have to do with UI, such as fragments, layout files, view models, etc, as well as any Android specific logic such as logic that needs to interact with the Android SDK.

The data module should only be concerned with network access and data structures, or any helper code that has nothing to do with the UI. The data module should never reference anything from the app module.

## Building and Packaging
### **build.gradle**
* In the app level [build.gradle](../app/build.gradle) file under *signingConfigs* you will find the reference to the keystore details. It’s highly recommended you move the password to an environment variable, or more preferably a CI variable in your pipeline.

* Under *defaultConfig* you will find the versioning scheme using the four variables - major, minor, patch, hotfix. There’s a method in there that creates the proper version code based on those variables. Always increment prior to uploading a new build to the Google Play Console.

### **Variants and Product Flavors**
When running the app locally, it's recommended to use the *internalDebug* variant since that is already enabled for debugging and it also will be pointed at the Devnet chain. If you select *productionRelease* the app will be pointed at Mainnet so use caution when selecting this option.

### **Packaging**
To package the app to upload to the Google Play console from Android Studio follow these steps
1. From the Build menu select *Generate signed bundle/apk*
2. Select App Bundle
3. Fill in the keystore details (you can find the correct values to enter in the build.gradle file as mentioned above)
4. Choose the correct build configuration (*internalBeta* if you want the app to use devnet. *productionRelease* if you want the app to use Mainnet)
5. Open the Event Log and click the *Locate* link to find the aab file you just built

## UI
* [DataBinding](https://developer.android.com/topic/libraries/data-binding) is used for most, if not all, layouts
* RecyclerViews use [Groupie](https://github.com/lisawray/groupie) which makes for seamless recycler view setups

### **Notes on Groupie**

Groupie is a simple, flexible library for complex RecyclerView layouts. 

It’s important to call out a few gotchas when using this library. Let’s take a look at an example that already exists in the app — the *Transaction History screen*. These are the files involved:
* [TransactionHistoryFragment](../app/src/main/java/org/aerovek/chartr/ui/wallet/transaction/TransactionHistoryFragment.kt)
* [TransactionHistoryViewModel](../app/src/main/java/org/aerovek/chartr/ui/wallet/transaction/TransactionHistoryViewModel.kt)
* [TransactionItemViewModel](../app/src/main/java/org/aerovek/chartr/ui/adapterItems/viewmodels/TransactionItemViewModel.kt) - instead of setting several variables in the *data* block of the layout file, this single object has all the data we need, then our data labels bind to the properties in this class
* [TransactionItem](../app/src/main/java/org/aerovek/chartr/ui/adapterItems/TransactionItem.kt) is responsible for binding the data for each individual transaction item, and is required for Groupie configuration (explained below in more detail)
* [transaction_item.xml](../app/src/main/res/layout/transaction_item.xml) - the layout file for our transaction item

The fragment will set up the recyclerView that will display all our transactions, and all we need to do is create a layout manager and set the adapter to a ```GroupAdapter<GroupieViewHolder>```. This will work in most cases, more complicated recycler view layouts might require more setup.
```
private fun setupRecyclerView(recyclerView: RecyclerView) {
   recyclerView.apply {
       layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL, false
       )
       adapter = GroupAdapter<GroupieViewHolder>()
   }
}
```

The view model retrieves the transaction data from the blockchain and builds a list of TransactionItemViewModel objects with the data we want to display in each adapter item. The view model sends that list back to the fragment and here’s what happens next…

The groupie adapter needs to work with something called a BindableItem class. So we create our own TransactionItem class that extends ```BindableItem<TransactionItemBinding>```, then override the required methods:

* ```bind()``` - use this method to set variable values in your xml layout, in this example we have a “vm” variable that we set the TransactionItemViewModel to. You can also change UI state directly via the viewBinding object passed in. In this example I change the transaction status label text color based on the transaction status value

* ```getLayout()``` - this should return the xml layout file for the transaction item
* ```initializeViewBinding()``` - this should return the result of DataBindingUtil.bind(view)

TransactionItemBinding is a generated class that represents our layout file, same as our fragment bindings. So the layout xml file needs to be created first before the TransactionItem class can be finished otherwise the TransactionItemBinding would not exist yet.

Back to the fragment. Once we have the TransactionItem class filled out, we map the list of TransactionItemViewModel objects sent back to the fragment into a new list of TransactionItem objects and that list is what we ultimately set the recyclerView’s data items with.

```
viewModel.transactionItemModels.observe(viewLifecycleOwner) { models ->
    val items = models.map {
        TransactionItem(requireContext(), it)
    }

    binding.recyclerView.setDataItems(items)
    viewModel.showLoading.postValue(false)
    binding.swipeRefreshLayout.isRefreshing = false
}
```

### **Dependency Injection**
Refer to [AppModules](../app/src/main/java/org/aerovek/chartr/AppModules.kt) to see how we set up our dependency structure. For example, we register view models using the viewModel dsl extension and pass whatever dependencies we need into the view model constructor (such as repository interfaces). The view model instance can then be referenced in the associated fragment like this:
```
    private val viewModel: CreateAccountViewModel by inject()
```

### **Activities and Fragments**

The Chartr app is a single activity app, meaning there is only the MainActivity. Well sort of, the onboarding flow uses its own activity but once onboarding is done that activity finishes and the rest of the app uses the MainActivity throughout. So since onboarding is done only once for a user (as long as they complete it without force quitting the app) after they install the app, I still consider it a single activity app.

With that said, each screen should have its own fragment and view model class. So when creating a new fragment select the option with viewmodel as shown below ![here](../docs/images/create_fragment_example.png) 

Also DO NOT name the layout like “fragment_edit_account” because the databinding framework will generate classes that represent this layout and you will be referencing these classes in code, so you don’t want to generate a class called “FragmentEditAccountBinding”. Instead, name it something like “edit_account_fragment” so you’ll end up with a generated binding class called “EditAccountFragmentBinding” which is more readable and intuitive.

### Asynchronous Operations
When making network calls or performing any long running asynchronous operation, always use the [DispatcherProvider](../app/src/main/java/org/aerovek/chartr/util/DispatcherProvider.kt) implementation with the ```viewModelScope.launch``` call to run a coroutine in the background. This allows you to easily specify which thread you want to run that block of code on (UI thread vs background thread). There are plenty of examples in the code on how to do this properly. 

For example, this line will run the code block in a background thread
```
viewModelScope.launch(dispatcherProvider.IO)
```

While this will run the block on the main UI thread
```
viewModelScope.launch(dispatcherProvider.MAIN)

```

You may find yourself needing to modify the UI within a callback that was originally executed on the IO thread, in which case you need to launch a new routine on the MAIN thread and within that block you can access the UI without the OS barfing o