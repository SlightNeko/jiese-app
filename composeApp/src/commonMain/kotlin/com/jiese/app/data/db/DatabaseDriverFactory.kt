package com.jiese.app.data.db

import app.cash.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory(context: PlatformContext) {
    fun createDriver(): SqlDriver
}
