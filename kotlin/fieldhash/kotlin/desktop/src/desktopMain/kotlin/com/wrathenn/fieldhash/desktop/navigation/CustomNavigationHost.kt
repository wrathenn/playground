package com.wrathenn.fieldhash.desktop.navigation

import androidx.compose.runtime.Composable
import com.wrathenn.fieldhash.desktop.view.PartitionChainsView
import com.wrathenn.fieldhash.desktop.vm.PartitionChainListViewModel

@Composable
fun CustomNavigationHost(
    navController: NavController
) {
    NavHost(navController) {
        composable(Screen.PartitionChainsScreen.name) {
            PartitionChainsView(
                viewModel = PartitionChainListViewModel(),
                navController = navController,
            )
        }

    }.build()
}
