package com.jiese.app.ui.running

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jiese.app.AppState
import com.jiese.app.data.model.RunningRecord
import com.jiese.app.network.AiApiClient
import kotlinx.datetime.Clock
import top.yukonga.miuix.kmp.basic.*

@Composable
fun RunningScreen() {
    val repo = AppState.repository
    val aiClient = remember { AiApiClient(repo) }
    var records by remember { mutableStateOf<List<RunningRecord>>(emptyList()) }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        records = repo.getAllRunningRecords()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = "🏃 跑步记录",
                modifier = Modifier.statusBarsPadding()
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true }
            ) {
                SmallTitle(text = "+")
            }
        }
    ) {
        if (records.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("暂无跑步记录", fontSize = 16.sp)
                    Text("点击右下角 + 添加", fontSize = 14.sp)
                }
            }
        } else {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                records.forEach { record ->
                    RunningRecordCard(record)
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }

    if (showDialog) {
        RecordRunningDialog(
            aiClient = aiClient,
            onDismiss = { showDialog = false },
            onConfirm = { dist, dur, avgHr, maxHr, pace, avgCad, maxCad, zones, score ->
                val now = Clock.System.now().toEpochMilliseconds()
                val totalScore = repo.getSetting("total_score")?.toDoubleOrNull() ?: 0.0
                val newTotal = totalScore + score
                repo.setSetting("total_score", newTotal.toString())
                repo.insertRunningRecord(now, dist, dur, avgHr, maxHr, pace, avgCad, maxCad, null, null, score)
                records = repo.getAllRunningRecords()
                showDialog = false
            }
        )
    }
}

@Composable
private fun RunningRecordCard(record: RunningRecord) {
    val dateStr = kotlinx.datetime.Instant.fromEpochMilliseconds(record.timestamp)
        .toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
    val date = "${dateStr.year}-${dateStr.monthNumber.toString().padStart(2, '0')}-${dateStr.dayOfMonth.toString().padStart(2, '0')}"
    val time = "${dateStr.hour.toString().padStart(2, '0')}:${dateStr.minute.toString().padStart(2, '0')}"

    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("$date  $time", fontSize = 14.sp)
                Spacer(Modifier.height(4.dp))
                Text("${record.distanceKm} km · ${record.durationSec / 60} 分钟", fontSize = 16.sp)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("+${record.earnedScore}", fontSize = 18.sp)
                record.avgHr?.let { Text("♥ $it", fontSize = 12.sp) }
            }
        }
    }
}
