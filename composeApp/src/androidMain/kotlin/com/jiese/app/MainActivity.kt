package com.jiese.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jiese.app.data.db.DatabaseDriverFactory
import com.jiese.app.db.JieSeDatabase
import com.jiese.app.data.repository.AppRepository
import com.jiese.app.ui.theme.JieSeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val driver = DatabaseDriverFactory(PlatformContext(this)).createDriver()
        val database = JieSeDatabase(driver)
        val repository = AppRepository(database)
        AppState.repository = repository

        setContent {
            JieSeTheme {
                App()
            }
        }
    }
}
