package com.wrathenn.fieldhash.desktop.navigation

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VerticalNavigationRail(navController: NavController) {
    NavigationRail(
        modifier = Modifier.fillMaxHeight(),
        header = {
            Text(
                text = "Navigation",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(16.dp)
            )
        }
    ) {
        // Define the navigation options
        for (screen in Screen.entries) {
            NavigationRailItem(
                selected = navController.currentScreen.value == screen.name,
                onClick = { navController.navigate(screen.name) },
                icon = { screen.icon },
                label = { Text(screen.name) }
            )
        }
    }
}
