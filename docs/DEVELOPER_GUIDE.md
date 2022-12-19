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

The app module