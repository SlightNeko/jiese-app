package com.jiese.app.data.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

actual class DatabaseDriverFactory actual constructor(private val platformContext: PlatformContext) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = JieSeDatabase.Schema,
            context = platformContext.context,
            name = "jiese.db"
        )
    }
}
