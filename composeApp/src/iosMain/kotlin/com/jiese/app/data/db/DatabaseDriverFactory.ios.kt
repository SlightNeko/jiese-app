package com.jiese.app.data.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual class DatabaseDriverFactory actual constructor(context: PlatformContext) {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = JieSeDatabase.Schema,
            name = "jiese.db"
        )
    }
}
