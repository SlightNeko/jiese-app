package com.jiese.app

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.jiese.app.ui.home.HomeScreen
import com.jiese.app.ui.stats.StatsScreen
import com.jiese.app.ui.running.RunningScreen
import com.jiese.app.ui.settings.SettingsScreen
import top.yukonga.miuix.kmp.basic.NavigationBar
import top.yukonga.miuix.kmp.basic.NavigationBarItem
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.icon.MiuixIcons

enum class Screen(val label: String, val icon: ImageVector) {
    Home("首页", MiuixIcons.Home),
    Stats("统计", MiuixIcons.All),
    Running("跑步", MiuixIcons.Location),
    Settings("设置", MiuixIcons.Settings)
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
                        icon = screen.icon,
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
