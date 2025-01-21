package com.wrathenn.fieldhash.desktop.navigation

import androidx.compose.runtime.Composable

class NavHost(
    val navController: NavController,
    val contents: @Composable NavGraphBuilder.() -> Unit
) {

    @Composable
    fun build() {
        NavGraphBuilder().renderContents()
    }

    inner class NavGraphBuilder(
        val navController: NavController = this@NavHost.navController
    ) {
        @Composable
        fun renderContents() {
            this@NavHost.contents(this)
        }
    }
}


/**
 * Composable to build the Navigation Host
 */
@Composable
fun NavHost.NavGraphBuilder.composable(
    route: String,
    content: @Composable () -> Unit
) {
    if (navController.currentScreen.value == route) {
        content()
    }
}

