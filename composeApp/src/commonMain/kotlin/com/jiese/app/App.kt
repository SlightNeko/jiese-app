package com.jiese.app

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
    var currentScreen by remember { mutableStateOf(Screen.Home) }

    Scaffold(
        bottomBar = {
            BottomNavigation(
                items = Screen.entries.map { screen ->
                    BottomNavigation.BottomNavigationItem(
                        label = screen.label,
                        selected = currentScreen == screen,
                        onClick = { currentScreen = screen },
                        icon = {
                            SmallTitle(text = when (screen) {
                                Screen.Home -> "🏠"
                                Screen.Stats -> "📊"
                                Screen.Running -> "🏃"
                                Screen.Settings -> "⚙️"
                            })
                        }
                    )
                }
            )
        }
    ) { padding ->
        val mod = Modifier.padding(padding)
        when (currentScreen) {
            Screen.Home -> HomeScreen()
            Screen.Stats -> StatsScreen()
            Screen.Running -> RunningScreen()
            Screen.Settings -> SettingsScreen()
        }
    }
}
