package com.jiese.app

import com.jiese.app.data.repository.AppRepository
import com.jiese.app.domain.ScoreEngine

object AppState {
    lateinit var repository: AppRepository
    val scoreEngine = ScoreEngine()
}
