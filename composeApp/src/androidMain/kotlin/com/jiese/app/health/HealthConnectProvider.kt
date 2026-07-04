package com.jiese.app.health

import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

class HealthConnectProvider(private val context: Context) {

    private val healthConnectClient by lazy {
        HealthConnectClient.getOrCreate(context)
    }

    suspend fun isAvailable(): Boolean {
        return try {
            val availability = healthConnectClient.sdkStatus
            availability == androidx.health.connect.client.SdkStatus.AVAILABLE
        } catch (e: Exception) {
            false
        }
    }

    suspend fun requestSleepPermissions() {
        val permissions = setOf(
            androidx.health.connect.client.permission.HealthPermission
                .getReadPermission(SleepSessionRecord::class)
        )
        try {
            healthConnectClient.permissionController.getGrantedPermissions()
            // If not granted, launch permission request
        } catch (e: Exception) {
            // Handle permission request
        }
    }

    suspend fun getSleepDuration(date: LocalDate): Int? {
        return try {
            val startOfDay = date.atStartOfDay(ZoneId.systemDefault()).toInstant()
            val endOfDay = date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()

            val response = healthConnectClient.readRecords(
                ReadRecordsRequest(
                    recordType = SleepSessionRecord::class,
                    timeRangeFilter = TimeRangeFilter.between(startOfDay, endOfDay)
                )
            )

            response.records.firstOrNull()?.let { sleepRecord ->
                val start = sleepRecord.startTime
                val end = sleepRecord.endTime
                val minutes = java.time.Duration.between(start, end).toMinutes()
                minutes.toInt()
            }
        } catch (e: Exception) {
            null
        }
    }
}
