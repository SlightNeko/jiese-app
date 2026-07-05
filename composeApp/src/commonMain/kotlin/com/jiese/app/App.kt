package com.jiese.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.jiese.app.ui.home.HomeScreen
import com.jiese.app.ui.stats.StatsScreen
import com.jiese.app.ui.running.RunningScreen
import com.jiese.app.ui.settings.SettingsScreen
import top.yukonga.miuix.kmp.basic.NavigationBar
import top.yukonga.miuix.kmp.basic.NavigationBarItem
import top.yukonga.miuix.kmp.basic.Scaffold

private val IconHome = ImageVector.Builder(
    name = "Home", defaultWidth = 24.dp, defaultHeight = 24.dp,
    viewportWidth = 24f, viewportHeight = 24f
).apply {
    addPath(
        pathData = listOf(
            androidx.compose.ui.graphics.vector.PathNode.MoveTo(12f, 3f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(2f, 12f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(5f, 12f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(5f, 21f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(10f, 21f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(10f, 14f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(14f, 14f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(14f, 21f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(19f, 21f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(19f, 12f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(22f, 12f),
            androidx.compose.ui.graphics.vector.PathNode.Close,
        ), fill = SolidColor(Color.Black), pathFillType = PathFillType.EvenOdd
    )
}.build()

private val IconStats = ImageVector.Builder(
    name = "Stats", defaultWidth = 24.dp, defaultHeight = 24.dp,
    viewportWidth = 24f, viewportHeight = 24f
).apply {
    addPath(
        pathData = listOf(
            androidx.compose.ui.graphics.vector.PathNode.MoveTo(4f, 21f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(4f, 14f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(8f, 14f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(8f, 21f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(4f, 21f),
            androidx.compose.ui.graphics.vector.PathNode.MoveTo(10f, 21f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(10f, 9f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(14f, 9f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(14f, 21f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(10f, 21f),
            androidx.compose.ui.graphics.vector.PathNode.MoveTo(16f, 21f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(16f, 3f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(20f, 3f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(20f, 21f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(16f, 21f),
            androidx.compose.ui.graphics.vector.PathNode.Close,
        ), fill = SolidColor(Color.Black), pathFillType = PathFillType.EvenOdd
    )
}.build()

private val IconRun = ImageVector.Builder(
    name = "Run", defaultWidth = 24.dp, defaultHeight = 24.dp,
    viewportWidth = 24f, viewportHeight = 24f
).apply {
    addPath(
        pathData = listOf(
            androidx.compose.ui.graphics.vector.PathNode.MoveTo(13.5f, 5.5f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(14.33f, 5.5f, 15f, 4.83f, 15f, 4f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(15f, 3.17f, 14.33f, 2.5f, 13.5f, 2.5f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(12.67f, 2.5f, 12f, 3.17f, 12f, 4f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(12f, 4.83f, 12.67f, 5.5f, 13.5f, 5.5f),
            androidx.compose.ui.graphics.vector.PathNode.MoveTo(9.89f, 19.38f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(10.89f, 15.94f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(13f, 18f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(13f, 22f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(15f, 22f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(15f, 16.5f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(10.88f, 12.47f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(12f, 9f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(13f, 10f, 14.5f, 11f, 16f, 11f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(16f, 9f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(14.5f, 9f, 13.5f, 8f, 12.88f, 7f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(11.78f, 5.27f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(11.38f, 4.64f, 10.68f, 4.26f, 9.94f, 4.26f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(9.74f, 4.26f, 9.54f, 4.29f, 9.34f, 4.36f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(5.5f, 5.5f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(5.5f, 7.5f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(8.73f, 6.62f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(6.97f, 13.03f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(3f, 14.97f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(3f, 17.03f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(8.21f, 14.26f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(9.89f, 19.38f),
            androidx.compose.ui.graphics.vector.PathNode.Close,
        ), fill = SolidColor(Color.Black), pathFillType = PathFillType.EvenOdd
    )
}.build()

private val IconSettings = ImageVector.Builder(
    name = "Settings", defaultWidth = 24.dp, defaultHeight = 24.dp,
    viewportWidth = 24f, viewportHeight = 24f
).apply {
    addPath(
        pathData = listOf(
            androidx.compose.ui.graphics.vector.PathNode.MoveTo(19.14f, 12.94f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(19.18f, 12.64f, 19.2f, 12.33f, 19.2f, 12f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(19.2f, 11.68f, 19.18f, 11.36f, 19.13f, 11.06f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(21.16f, 9.48f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(21.34f, 9.34f, 21.39f, 9.07f, 21.28f, 8.87f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(19.36f, 5.55f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(19.24f, 5.33f, 18.99f, 5.26f, 18.77f, 5.33f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(16.38f, 6.29f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(15.88f, 5.91f, 15.35f, 5.59f, 14.76f, 5.35f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(14.4f, 2.81f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(14.36f, 2.57f, 14.16f, 2.4f, 13.91f, 2.4f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(10.09f, 2.4f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(9.84f, 2.4f, 9.64f, 2.57f, 9.6f, 2.81f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(9.24f, 5.35f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(8.65f, 5.59f, 8.12f, 5.92f, 7.62f, 6.29f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(5.23f, 5.33f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(5.02f, 5.26f, 4.77f, 5.33f, 4.64f, 5.55f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(2.72f, 8.87f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(2.61f, 9.08f, 2.66f, 9.34f, 2.84f, 9.48f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(4.87f, 11.06f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(4.82f, 11.36f, 4.8f, 11.69f, 4.8f, 12f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(4.8f, 12.31f, 4.82f, 12.64f, 4.87f, 12.94f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(2.84f, 14.52f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(2.66f, 14.66f, 2.61f, 14.93f, 2.72f, 15.13f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(4.64f, 18.45f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(4.76f, 18.67f, 5.01f, 18.74f, 5.23f, 18.67f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(7.62f, 17.71f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(8.12f, 18.09f, 8.65f, 18.41f, 9.24f, 18.65f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(9.6f, 21.19f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(9.64f, 21.43f, 9.84f, 21.6f, 10.09f, 21.6f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(13.91f, 21.6f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(14.16f, 21.6f, 14.36f, 21.43f, 14.4f, 21.19f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(14.76f, 18.65f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(15.35f, 18.41f, 15.88f, 18.09f, 16.38f, 17.71f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(18.77f, 18.67f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(18.98f, 18.74f, 19.23f, 18.67f, 19.36f, 18.45f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(21.28f, 15.13f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(21.39f, 14.92f, 21.34f, 14.66f, 21.16f, 14.52f),
            androidx.compose.ui.graphics.vector.PathNode.LineTo(19.14f, 12.94f),
            androidx.compose.ui.graphics.vector.PathNode.Close,
            androidx.compose.ui.graphics.vector.PathNode.MoveTo(12f, 15.2f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(10.23f, 15.2f, 8.8f, 13.77f, 8.8f, 12f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(8.8f, 10.23f, 10.23f, 8.8f, 12f, 8.8f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(13.77f, 8.8f, 15.2f, 10.23f, 15.2f, 12f),
            androidx.compose.ui.graphics.vector.PathNode.CurveTo(15.2f, 13.77f, 13.77f, 15.2f, 12f, 15.2f),
            androidx.compose.ui.graphics.vector.PathNode.Close,
        ), fill = SolidColor(Color.Black), pathFillType = PathFillType.EvenOdd
    )
}.build()

enum class Screen(val label: String, val icon: ImageVector) {
    Home("首页", IconHome),
    Stats("统计", IconStats),
    Running("跑步", IconRun),
    Settings("设置", IconSettings)
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
