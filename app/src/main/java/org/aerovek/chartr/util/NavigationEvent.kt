package org.aerovek.chartr.util

import androidx.navigation.NavController
import androidx.navigation.NavDirections

sealed class NavigationEvent {
    class Directions(val directions: NavDirections) : NavigationEvent()
    class Action(val destinationId: Int) : NavigationEvent()
    class Back(val destinationId: Int = -1, val inclusive: Boolean = false) : NavigationEvent()

    fun na