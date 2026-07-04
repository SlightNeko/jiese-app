package com.jiese.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.theme.ThemeController
import top.yukonga.miuix.kmp.theme.colorScheme.lightColorScheme
import top.yukonga.miuix.kmp.theme.colorScheme.darkColorScheme

val Green500 = Color(0xFF4CAF50)
val Green700 = Color(0xFF388E3C)
val Amber500 = Color(0xFFFFC107)
val DeepOrange400 = Color(0xFFFF7043)

@Composable
fun JieSeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val controller = remember {
        ThemeController(
            keyColor = Green500
        )
    }

    val colors = if (darkTheme) {
        darkColorScheme(
            primary = Green500,
            onPrimary = Color.White,
            secondary = Amber500,
            error = DeepOrange400,
            surface = Color(0xFF1C1B1F),
            background = Color(0xFF121212),
        )
    } else {
        lightColorScheme(
            primary = Green700,
            onPrimary = Color.White,
            secondary = Amber500,
            error = DeepOrange400,
            surface = Color(0xFFFFFBFE),
            background = Color(0xFFF5F5F5),
        )
    }

    MiuixTheme(
        colors = colors,
        controller = controller,
        content = content
    )
}
