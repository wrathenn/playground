package com.wrathenn.fieldhash.desktop

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.wrathenn.fieldhash.desktop.navigation.CustomNavigationHost
import com.wrathenn.fieldhash.desktop.navigation.NavController.Companion.navControllerState
import com.wrathenn.fieldhash.desktop.navigation.Screen
import com.wrathenn.fieldhash.desktop.navigation.VerticalNavigationRail

@Composable
@Preview
fun App() {
  var text by remember { mutableStateOf("Asd!") }
  var boxSize by remember { mutableFloatStateOf(64f) }

  val alphaAnimation by animateFloatAsState(
    targetValue = boxSize,
    animationSpec = tween(durationMillis = 1727)
  )

  MaterialTheme {
    Scaffold { padding ->
      Column(modifier = Modifier.fillMaxWidth().padding(padding)) {
        Button(onClick = { text = "Dsa!"; boxSize += 3 }) { Text(text) }

        Box(modifier = Modifier.size(alphaAnimation.dp).border(width = 1.dp, color = Color.Red))
        Box(modifier = Modifier.size(alphaAnimation.dp).border(width = 1.dp, color = Color.Red))
        Box(modifier = Modifier.size(alphaAnimation.dp).border(width = 1.dp, color = Color.Red))
        Box(modifier = Modifier.size(alphaAnimation.dp).border(width = 1.dp, color = Color.Red))
        Box(modifier = Modifier.size(alphaAnimation.dp).border(width = 1.dp, color = Color.Red))
      }
    }
  }
}

fun main() = application {
  Window(onCloseRequest = ::exitApplication) {
    App2()
  }
}

@Composable
fun App2() {
  val screens = Screen.entries
  val navController by navControllerState(Screen.PartitionChainsScreen.name)
  val currentScreen by remember { navController.currentScreen }

  Row(Modifier.fillMaxSize()) {
    VerticalNavigationRail(navController)

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
      CustomNavigationHost(navController = navController)
    }
  }
}
