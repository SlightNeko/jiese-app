package com.jiese.app

import androidx.compose.runtime.*
import com.jiese.app.ui.home.HomeScreen
import com.jiese.app.ui.stats.StatsScreen
import com.jiese.app.ui.running.RunningScreen
import com.jiese.app.ui.settings.SettingsScreen
import top.yukonga.miuix.kmp.basic.NavigationBar
import top.yukonga.miuix.kmp.basic.NavigationBarItem
import top.yukonga.miuix.kmp.basic.Scaffold

enum class Screen(val label: String) {
    Home("首页"),
    Stats("统计"),
    Running("跑步"),
    Settings("设置")
}

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf(Screen.Home) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                Screen.entries.forEach { screen ->
                    NavigationBarItem(
                        selected = currentScreen == screen,
                        onClick = { currentScreen = screen },
                        label = screen.label,
                    )
                }
            }
        }
    ) {
        when (currentScreen) {
            Screen.Home -> HomeScreen()
            Screen.Stats -> StatsScreen()
            Screen.Running -> RunningScreen()
            Screen.Settings -> SettingsScreen()
        }
    }
}
