package com.jiese.app

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.jiese.app.ui.home.HomeScreen
import com.jiese.app.ui.stats.StatsScreen
import com.jiese.app.ui.running.RunningScreen
import com.jiese.app.ui.settings.SettingsScreen
import top.yukonga.miuix.kmp.basic.NavigationBar
import top.yukonga.miuix.kmp.basic.NavigationBarItem
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
    var currentScreen by remember { mutableStateOf(Screen.Home) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                Screen.entries.forEachIndexed { index, screen ->
                    NavigationBarItem(
                        selected = currentScreen == screen,
                        onClick = { currentScreen = screen },
                        icon = {
                            SmallTitle(text = when (screen) {
                                Screen.Home -> "\uD83C\uDFE0"
                                Screen.Stats -> "\uD83D\uDCCA"
                                Screen.Running -> "\uD83C\uDFC3"
                                Screen.Settings -> "\u2699\uFE0F"
                            })
                        },
                        label = screen.label,
                    )
                }
            }
        }
    ) { padding ->
        when (currentScreen) {
            Screen.Home -> HomeScreen()
            Screen.Stats -> StatsScreen()
            Screen.Running -> RunningScreen()
            Screen.Settings -> SettingsScreen()
        }
    }
}
