package com.jiese.app

import com.jiese.app.data.repository.AppRepository

object AppState {
    lateinit var repository: AppRepository
    val scoreEngine = domain.ScoreEngine()
}
