package com.jiese.app.domain

import com.jiese.app.data.model.HrZones
import kotlin.math.min
import kotlin.math.max
import kotlin.math.floor

class ScoreEngine {

    fun calculateRunningScore(
        distanceKm: Double,
        durationMin: Double,
        avgHr: Int?,
        hrZones: HrZones?
    ): Double {
        val distanceScore = min(distanceKm, 15.0) * 0.4

        val intensityScore = if (hrZones != null) {
            hrZones.z1 * 0.02 +
            hrZones.z2 * 0.08 +
            hrZones.z3 * 0.06 +
            hrZones.z4 * 0.04 +
            hrZones.z5 * 0.02
        } else if (avgHr != null) {
            (durationMin * 0.05) + (distanceScore * 0.3)
        } else {
            distanceKm * 0.5 + durationMin * 0.05
        }

        return min(distanceScore + intensityScore, 10.0)
    }

    fun calculateSleepScore(totalMinutes: Int): Double {
        val hours = totalMinutes / 60.0
        return when {
            hours in 7.0..9.0 -> 3.0
            hours in 6.0..<7.0 || hours in 9.0..<10.0 -> 1.0
            hours in 5.0..<6.0 -> -1.0
            hours < 5.0 || hours >= 10.0 -> -3.0
            else -> 0.0
        }
    }

    fun calculateRelapsePenalty(daysSinceLastRelapse: Int): Double {
        return when {
            daysSinceLastRelapse < 3 -> -10.0
            daysSinceLastRelapse < 14 -> -8.0
            else -> -5.0
        }
    }

    fun calculateMilestoneBonus(streakDays: Int): Double {
        return when (streakDays) {
            7 -> 5.0
            14 -> 10.0
            30 -> 20.0
            90 -> 50.0
            365 -> 100.0
            else -> 0.0
        }
    }

    fun calculateCurrentStreak(latestRelapseTimestamp: Long?, now: Long): Int {
        if (latestRelapseTimestamp == null) return 0

        val relapseDate = toDateMillis(latestRelapseTimestamp)
        val todayDate = toDateMillis(now)
        val diff = (todayDate - relapseDate) / MILLIS_PER_DAY

        return if (diff == 0L && isToday(latestRelapseTimestamp, now)) 0 else max(0, diff.toInt())
    }

    fun daysSinceLastRelapse(latestRelapseTimestamp: Long?, now: Long): Int {
        if (latestRelapseTimestamp == null) return Int.MAX_VALUE

        val relapseDate = toDateMillis(latestRelapseTimestamp)
        val nowDate = toDateMillis(now)
        return ((nowDate - relapseDate) / MILLIS_PER_DAY).toInt()
    }

    fun getDayStartMillis(epochMillis: Long): Long {
        return toDateMillis(epochMillis)
    }

    fun getDayEndMillis(epochMillis: Long): Long {
        return toDateMillis(epochMillis) + MILLIS_PER_DAY
    }

    companion object {
        const val MILLIS_PER_DAY = 86400000L

        fun toDateMillis(epochMillis: Long): Long {
            return (epochMillis / MILLIS_PER_DAY) * MILLIS_PER_DAY
        }

        fun isToday(timestamp: Long, now: Long): Boolean {
            return toDateMillis(timestamp) == toDateMillis(now)
        }
    }
}
