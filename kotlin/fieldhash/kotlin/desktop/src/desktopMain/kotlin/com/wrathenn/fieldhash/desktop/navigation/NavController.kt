package com.wrathenn.fieldhash.desktop.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable

/**
 * NavController Class
 */
class NavController(
    private val startDestination: String,
    private var backStackScreens: MutableList<String> = mutableListOf()
) {
    // Variable to store the state of the current screen
    var currentScreen: MutableState<String> = mutableStateOf(startDestination)

    // Function to handle the navigation between the screen
    fun navigate(route: String) {
        if (route != currentScreen.value) {
            if (backStackScreens.contains(currentScreen.value) && currentScreen.value != startDestination) {
                backStackScreens.remove(currentScreen.value)
            }

            if (route == startDestination) {
                backStackScreens = mutableListOf()
            } else {
                backStackScreens.add(currentScreen.value)
            }

            currentScreen.value = route
        }
    }

    // Function to handle the back
    fun navigateBack() {
        if (backStackScreens.isNotEmpty()) {
            currentScreen.value = backStackScreens.last()
            backStackScreens.remove(currentScreen.value)
        }
    }

    companion object {
        @Composable
        fun navControllerState(
            startDestination: String,
            backStackScreens: MutableList<String> = mutableListOf(),
        ): MutableState<NavController> = rememberSaveable {
            mutableStateOf(NavController(startDestination, backStackScreens))
        }
    }
}

