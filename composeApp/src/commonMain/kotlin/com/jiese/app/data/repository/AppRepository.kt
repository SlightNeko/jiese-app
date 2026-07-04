package com.jiese.app.data.repository

import com.jiese.app.db.JieSeDatabase
import com.jiese.app.data.model.*

class AppRepository(private val db: JieSeDatabase) {

    // ===== Relapse =====
    fun getLatestRelapse(): RelapseRecord? {
        return db.jieSeDatabaseQueries.getLatestRelapse().executeAsOneOrNull()?.let {
            RelapseRecord(id = it.id, timestamp = it.timestamp, note = it.note)
        }
    }

    fun getTodayRelapses(dayStart: Long, dayEnd: Long): List<RelapseRecord> {
        return db.jieSeDatabaseQueries.getTodayRelapses(dayStart, dayEnd).executeAsList().map {
            RelapseRecord(id = it.id, timestamp = it.timestamp, note = it.note)
        }
    }

    fun getRelapseCountByDateRange(start: Long, end: Long): Long {
        return db.jieSeDatabaseQueries.getRelapseCountByDateRange(start, end).executeAsOne()
    }

    fun getRelapseCountByDate(dayStart: Long, dayEnd: Long): Long {
        return db.jieSeDatabaseQueries.getRelapseCountByDate(dayStart, dayEnd).executeAsOne()
    }

    fun getAllRelapses(): List<RelapseRecord> {
        return db.jieSeDatabaseQueries.getAllRelapses().executeAsList().map {
            RelapseRecord(id = it.id, timestamp = it.timestamp, note = it.note)
        }
    }

    fun getRelapsesBetween(start: Long, end: Long): List<RelapseRecord> {
        return db.jieSeDatabaseQueries.getRelapsesBetween(start, end).executeAsList().map {
            RelapseRecord(id = it.id, timestamp = it.timestamp, note = it.note)
        }
    }

    fun insertRelapse(timestamp: Long, note: String?) {
        db.jieSeDatabaseQueries.insertRelapse(timestamp, note)
    }

    fun deleteRelapse(id: Long) {
        db.jieSeDatabaseQueries.deleteRelapse(id)
    }

    // ===== Running =====
    fun getAllRunningRecords(): List<RunningRecord> {
        return db.jieSeDatabaseQueries.getAllRunningRecords().executeAsList().map { row ->
            RunningRecord(
                id = row.id,
                timestamp = row.timestamp,
                distanceKm = row.distance_km,
                durationSec = row.duration_sec.toInt(),
                avgHr = row.avg_hr,
                maxHr = row.max_hr,
                avgPace = row.avg_pace,
                avgCadence = row.avg_cadence,
                maxCadence = row.max_cadence,
                earnedScore = row.earned_score
            )
        }
    }

    fun getRunningRecordsBetween(start: Long, end: Long): List<RunningRecord> {
        return db.jieSeDatabaseQueries.getRunningRecordsBetween(start, end).executeAsList().map { row ->
            RunningRecord(
                id = row.id,
                timestamp = row.timestamp,
                distanceKm = row.distance_km,
                durationSec = row.duration_sec.toInt(),
                avgHr = row.avg_hr,
                maxHr = row.max_hr,
                avgPace = row.avg_pace,
                avgCadence = row.avg_cadence,
                maxCadence = row.max_cadence,
                earnedScore = row.earned_score
            )
        }
    }

    fun getRecentRunningRecords(limit: Int): List<RunningRecord> {
        return db.jieSeDatabaseQueries.getRecentRunningRecords(limit.toLong()).executeAsList().map { row ->
            RunningRecord(
                id = row.id,
                timestamp = row.timestamp,
                distanceKm = row.distance_km,
                durationSec = row.duration_sec.toInt(),
                avgHr = row.avg_hr,
                maxHr = row.max_hr,
                avgPace = row.avg_pace,
                avgCadence = row.avg_cadence,
                maxCadence = row.max_cadence,
                earnedScore = row.earned_score
            )
        }
    }

    fun getRunningStats(start: Long, end: Long): Triple<Double, Double, Long> {
        val stats = db.jieSeDatabaseQueries.getRunningStats(start, end).executeAsOne()
        return Triple(stats.total_distance, stats.total_score, stats.total_count)
    }

    fun insertRunningRecord(
        timestamp: Long, distanceKm: Double, durationSec: Int,
        avgHr: Int?, maxHr: Int?, avgPace: String?,
        avgCadence: Int?, maxCadence: Int?, hrZones: String?,
        aiAnalysis: String?, earnedScore: Double
    ) {
        db.jieSeDatabaseQueries.insertRunningRecord(
            timestamp, distanceKm, durationSec.toLong(),
            avgHr, maxHr, avgPace,
            avgCadence?.toLong(), maxCadence?.toLong(),
            hrZones, aiAnalysis, earnedScore
        )
    }

    // ===== Sleep =====
    fun getSleepRecord(date: Int): SleepRecord? {
        return db.jieSeDatabaseQueries.getSleepRecord(date.toLong()).executeAsOneOrNull()?.let {
            SleepRecord(
                id = it.id,
                date = it.date.toInt(),
                bedTime = it.bed_time,
                wakeTime = it.wake_time,
                totalMinutes = it.total_minutes.toInt(),
                source = it.source
            )
        }
    }

    fun upsertSleepRecord(date: Int, bedTime: Long?, wakeTime: Long?, totalMinutes: Int, source: String) {
        db.jieSeDatabaseQueries.upsertSleepRecord(
            date.toLong(), bedTime, wakeTime, totalMinutes.toLong(), source
        )
    }

    fun getSleepRecordsBetween(start: Int, end: Int): List<SleepRecord> {
        return db.jieSeDatabaseQueries.getSleepRecordsBetween(start.toLong(), end.toLong()).executeAsList().map {
            SleepRecord(
                id = it.id,
                date = it.date.toInt(),
                bedTime = it.bed_time,
                wakeTime = it.wake_time,
                totalMinutes = it.total_minutes.toInt(),
                source = it.source
            )
        }
    }

    // ===== Settings =====
    fun getSetting(key: String): String? {
        return db.jieSeDatabaseQueries.getSetting(key).executeAsOneOrNull()
    }

    fun setSetting(key: String, value: String) {
        db.jieSeDatabaseQueries.setSetting(key, value)
    }
}
