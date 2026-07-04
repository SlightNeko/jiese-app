package com.jiese.app

import androidx.compose.ui.window.ComposeUIViewController
import com.jiese.app.data.db.DatabaseDriverFactory
import com.jiese.app.db.JieSeDatabase
import com.jiese.app.data.repository.AppRepository
import com.jiese.app.ui.theme.JieSeTheme

fun MainViewController() = ComposeUIViewController {
    val driver = DatabaseDriverFactory(PlatformContext()).createDriver()
    val database = JieSeDatabase(driver)
    val repository = AppRepository(database)
    AppState.repository = repository

    JieSeTheme {
        App()
    }
}
