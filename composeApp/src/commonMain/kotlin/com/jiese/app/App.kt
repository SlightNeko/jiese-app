package com.jiese.app

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jiese.app.ui.home.HomeScreen
import com.jiese.app.ui.stats.StatsScreen
import com.jiese.app.ui.running.RunningScreen
import com.jiese.app.ui.settings.SettingsScreen
import top.yukonga.miuix.kmp.basic.BottomNavigation
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.basic.SmallTitle

enum class Screen(val label: String) {
    Home("首页"),
    Stats("统计"),
    Running("跑步"),
    Settings("设置")
}

@Composable
fun App() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryFlow.collectAsState(initial = null).value?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavigation(
                items = listOf(
                    BottomNavigation.BottomNavigationItem(
                        label = Screen.Home.label,
                        selected = currentRoute == Screen.Home.name,
                        onClick = { navController.navigate(Screen.Home.name) { popUpTo(0) { saveState = true }; launchSingleTop = true; restoreState = true } },
                        icon = { SmallTitle(text = "🏠") }
                    ),
                    BottomNavigation.BottomNavigationItem(
                        label = Screen.Stats.label,
                        selected = currentRoute == Screen.Stats.name,
                        onClick = { navController.navigate(Screen.Stats.name) { popUpTo(0) { saveState = true }; launchSingleTop = true; restoreState = true } },
                        icon = { SmallTitle(text = "📊") }
                    ),
                    BottomNavigation.BottomNavigationItem(
                        label = Screen.Running.label,
                        selected = currentRoute == Screen.Running.name,
                        onClick = { navController.navigate(Screen.Running.name) { popUpTo(0) { saveState = true }; launchSingleTop = true; restoreState = true } },
                        icon = { SmallTitle(text = "🏃") }
                    ),
                    BottomNavigation.BottomNavigationItem(
                        label = Screen.Settings.label,
                        selected = currentRoute == Screen.Settings.name,
                        onClick = { navController.navigate(Screen.Settings.name) { popUpTo(0) { saveState = true }; launchSingleTop = true; restoreState = true } },
                        icon = { SmallTitle(text = "⚙️") }
                    )
                )
            )
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.name,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Home.name) { HomeScreen() }
            composable(Screen.Stats.name) { StatsScreen() }
            composable(Screen.Running.name) { RunningScreen() }
            composable(Screen.Settings.name) { SettingsScreen() }
        }
    }
}
