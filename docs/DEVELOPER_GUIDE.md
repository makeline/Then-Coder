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

* Under *defaultConfig* you will find the versioning scheme using the four variables - major, minor, patch, hotfix. There’s a method in there that creates the proper version code based on those variables. Always in