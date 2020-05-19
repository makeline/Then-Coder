
/*
The MIT License (MIT)

Copyright (c) 2023-present Aerovek

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package org.aerovek.chartr.ui

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.aerovek.chartr.R
import org.aerovek.chartr.databinding.MainActivityBinding
import org.aerovek.chartr.util.CreateAccountObserver
import org.aerovek.chartr.util.DispatcherProvider
import org.koin.android.ext.android.inject

private lateinit var biometricPrompt: BiometricPrompt

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    val activityViewModel: MainActivityViewModel by inject()
    val sharedPreferences: SharedPreferences by inject()
    val dispatcherProvider: DispatcherProvider by inject()
    private lateinit var activityBinding: MainActivityBinding
    private lateinit var toolbarTextView: TextView
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        activityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        activityBinding.viewModel = activityViewModel
        navController = findNavController(R.id.nav_host_fragment)
        activityBinding.lifecycleOwner = this

        val toolbar: Toolbar = findViewById(R.id.toolbar)

        toolbarTextView = toolbar.findViewById(R.id.toolbarTextView)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        bottomNav = findViewById(R.id.navigation)
        setupBottomNav()

        val accountMenuItem = bottomNav.menu.findItem(R.id.myAccountFragment)
        accountMenuItem.isVisible = sharedPreferences.contains(AppConstants.UserPrefsKeys.ACCOUNT_TYPE)
                && sharedPreferences.contains(AppConstants.UserPrefsKeys.USER_PIN)

        // Listen for the account created event so we can show the Account menu item
        activityViewModel.createAccountEvent.observe(
            this,
            CreateAccountObserver(accountMenuItem)
        )

        //biometricPrompt = createBiometricPrompt()

        navController.addOnDestinationChangedListener { _, destination: NavDestination, _: Bundle? ->
            // Only show bottom nav when on home, wallet, or account fragments, so start with it hidden
            this.bottomNav.visibility = View.GONE

            when (destination.id) {
                R.id.splashFragment -> this.supportActionBar?.hide()
                R.id.createWalletFragment -> {
                    this.supportActionBar?.show()
                    this.toolbarTextView.text = getString(R.string.create_wallet)
                }
                R.id.walletFragment -> {
                    this.supportActionBar?.show()
                    this.toolbarTextView.text = getString(R.string.wallet_title)
                    this.bottomNav.visibility = View.VISIBLE
                }
                R.id.protectWalletFragment -> {
                    this.toolbarTextView.text = getString(R.string.protect_wallet_title)
                }
                R.id.protectWalletTipsFragment -> {
                    this.toolbarTextView.text = getString(R.string.protect_wallet_tips_title)
                }
                R.id.secretPhraseFragment -> {
                    this.toolbarTextView.text = getString(R.string.recovery_phrase_title)
                }
                R.id.importWalletFragment -> {
                    this.toolbarTextView.text = getString(R.string.import_wallet_title)
                }
                R.id.verifyWordsFragment -> {
                    this.toolbarTextView.text = getString(R.string.verify_words_title)
                }
                R.id.homeFragment -> {
                    this.supportActionBar?.hide()
                    this.bottomNav.visibility = View.VISIBLE
                }
                R.id.searchFragment -> {
                    this.supportActionBar?.show()
                    this.bottomNav.visibility = View.GONE
                    this.toolbarTextView.text = getString(R.string.search_flights_title)
                }
                R.id.createAccountFragment -> {
                    this.supportActionBar?.show()
                    this.bottomNav.visibility = View.GONE
                    this.toolbarTextView.text = getString(R.string.create_account_title)
                }
                R.id.myAccountFragment -> {
                    this.supportActionBar?.show()
                    this.toolbarTextView.text = getString(R.string.my_account_title)
                    this.bottomNav.visibility = View.VISIBLE
                }
                R.id.sendAeroFragment -> {
                    this.supportActionBar?.show()