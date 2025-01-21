package com.wrathenn.fieldhash.desktop.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Screens
 */
enum class Screen(
    val label: String,
    val icon: ImageVector
) {
    PartitionChainsScreen(label = "Home", icon = Icons.Filled.Home),
}
