package com.jiese.app.data.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.jiese.app.db.JieSeDatabase

actual class DatabaseDriverFactory actual constructor(private val context: PlatformContext) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = JieSeDatabase.Schema,
            context = context.context,
            name = "jiese.db"
        )
    }
}
