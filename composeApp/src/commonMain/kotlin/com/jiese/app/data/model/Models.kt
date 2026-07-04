package com.jiese.app.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RelapseRecord(
    val id: Long = 0,
    val timestamp: Long,
    val note: String? = null
)

@Serializable
data class RunningRecord(
    val id: Long = 0,
    val timestamp: Long,
    val distanceKm: Double,
    val durationSec: Int,
    val avgHr: Int? = null,
    val maxHr: Int? = null,
    val avgPace: String? = null,
    val avgCadence: Int? = null,
    val maxCadence: Int? = null,
    val hrZones: HrZones? = null,
    val aiAnalysis: AiAnalysis? = null,
    val earnedScore: Double = 0.0
)

@Serializable
data class HrZones(
    val z1: Int = 0,
    val z2: Int = 0,
    val z3: Int = 0,
    val z4: Int = 0,
    val z5: Int = 0
)

@Serializable
data class AiAnalysis(
    val healthScore: Double,
    val evaluation: String,
    val feedback: String
)

@Serializable
data class SleepRecord(
    val id: Long = 0,
    val date: Int,
    val bedTime: Long? = null,
    val wakeTime: Long? = null,
    val totalMinutes: Int,
    val source: String = "manual"
)

@Serializable
data class DailyScore(
    val date: Long,
    val baseScore: Double,
    val noRelapseBonus: Double,
    val runningScore: Double,
    val sleepScore: Double,
    val relapsePenalty: Double,
    val milestoneBonus: Double,
    val total: Double
)

data class AppSettings(
    val apiEndpoint: String = "",
    val apiKey: String = "",
    val modelName: String = "gpt-4o-mini",
    val startDate: Long? = null,
    val reminderEnabled: Boolean = false,
    val maxStreak: Int = 0,
    val totalRelapses: Int = 0
)

const val KEY_API_ENDPOINT = "api_endpoint"
const val KEY_API_KEY = "api_key"
const val KEY_MODEL_NAME = "model_name"
const val KEY_START_DATE = "start_date"
const val KEY_REMINDER_ENABLED = "reminder_enabled"
const val KEY_MAX_STREAK = "max_streak"
const val KEY_TOTAL_RELAPSES = "total_relapses"
