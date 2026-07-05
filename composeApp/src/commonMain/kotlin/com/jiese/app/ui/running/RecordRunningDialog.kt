package com.jiese.app.ui.running

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jiese.app.FormatUtils
import com.jiese.app.domain.ScoreEngine
import com.jiese.app.network.AiApiClient
import com.jiese.app.data.model.HrZones
import kotlinx.coroutines.launch
import top.yukonga.miuix.kmp.basic.*
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun RecordRunningDialog(
    aiClient: AiApiClient?,
    onDismiss: () -> Unit,
    onConfirm: (distanceKm: Double, durationSec: Int, avgHr: Int?, maxHr: Int?, avgPace: String?,
               avgCadence: Int?, maxCadence: Int?, hrZones: HrZones?, earnedScore: Double) -> Unit
) {
    val scope = rememberCoroutineScope()
    var distanceKm by remember { mutableStateOf("") }
    var durationMin by remember { mutableStateOf("") }
    var avgHr by remember { mutableStateOf("") }
    var maxHr by remember { mutableStateOf("") }
    var avgPace by remember { mutableStateOf("") }
    var avgCadence by remember { mutableStateOf("") }
    var maxCadence by remember { mutableStateOf("") }
    var z1 by remember { mutableStateOf("") }
    var z2 by remember { mutableStateOf("") }
    var z3 by remember { mutableStateOf("") }
    var z4 by remember { mutableStateOf("") }
    var z5 by remember { mutableStateOf("") }
    var calculatedScore by remember { mutableStateOf(0.0) }
    var isAnalyzing by remember { mutableStateOf(false) }

    val engine = remember { ScoreEngine() }

    fun calculateLocalScore(): Double {
        val dist = distanceKm.toDoubleOrNull() ?: 0.0
        val dur = durationMin.toDoubleOrNull() ?: 0.0
        val hr = avgHr.toIntOrNull()
        val zones = if (z1.isNotBlank() || z2.isNotBlank() || z3.isNotBlank() || z4.isNotBlank() || z5.isNotBlank()) {
            HrZones(
                z1 = z1.toIntOrNull() ?: 0,
                z2 = z2.toIntOrNull() ?: 0,
                z3 = z3.toIntOrNull() ?: 0,
                z4 = z4.toIntOrNull() ?: 0,
                z5 = z5.toIntOrNull() ?: 0
            )
        } else null
        return engine.calculateRunningScore(dist, dur, hr, zones)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "🏃 记录跑步",
                style = MiuixTheme.textStyles.title2
            )
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Row {
                    TextField(
                        value = distanceKm, onValueChange = { distanceKm = it },
                        label = "距离 km", modifier = Modifier.weight(1f).padding(end = 4.dp)
                    )
                    TextField(
                        value = durationMin, onValueChange = { durationMin = it },
                        label = "时长 分", modifier = Modifier.weight(1f).padding(start = 4.dp)
                    )
                }
                Spacer(Modifier.height(8.dp))
                Row {
                    TextField(
                        value = avgHr, onValueChange = { avgHr = it },
                        label = "平均心率", modifier = Modifier.weight(1f).padding(end = 4.dp)
                    )
                    TextField(
                        value = maxHr, onValueChange = { maxHr = it },
                        label = "最大心率", modifier = Modifier.weight(1f).padding(start = 4.dp)
                    )
                }
                Spacer(Modifier.height(8.dp))
                Row {
                    TextField(
                        value = avgPace, onValueChange = { avgPace = it },
                        label = "平均配速(/km)", modifier = Modifier.weight(1f).padding(end = 4.dp)
                    )
                    TextField(
                        value = avgCadence, onValueChange = { avgCadence = it },
                        label = "平均步频", modifier = Modifier.weight(1f).padding(start = 4.dp)
                    )
                }
                Spacer(Modifier.height(8.dp))

                Text("心率区间 (分钟)", style = MiuixTheme.textStyles.body1)
                Spacer(Modifier.height(4.dp))
                Row {
                    listOf("Z1", "Z2", "Z3", "Z4", "Z5").forEachIndexed { i, label ->
                        TextField(
                            value = when(i) { 0 -> z1; 1 -> z2; 2 -> z3; 3 -> z4; else -> z5 },
                            onValueChange = { v -> when(i) { 0 -> z1 = v; 1 -> z2 = v; 2 -> z3 = v; 3 -> z4 = v; else -> z5 = v } },
                            label = label, modifier = Modifier.weight(1f).padding(horizontal = 2.dp)
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = {
                        val local = calculateLocalScore()
                        calculatedScore = local
                        if (aiClient != null) {
                            scope.launch {
                                isAnalyzing = true
                                try {
                                    val aiResult = aiClient.analyzeRunning(
                                        distanceKm.toDoubleOrNull() ?: 0.0,
                                        durationMin.toDoubleOrNull() ?: 0.0,
                                        avgHr.toIntOrNull(),
                                        maxHr.toIntOrNull(),
                                        avgPace.ifBlank { null },
                                        avgCadence.toIntOrNull(),
                                        maxCadence.toIntOrNull(),
                                        HrZones(z1.toIntOrNull()?:0, z2.toIntOrNull()?:0, z3.toIntOrNull()?:0, z4.toIntOrNull()?:0, z5.toIntOrNull()?:0)
                                    )
                                    aiResult?.let {
                                        val adjusted = (local + it.healthScore).coerceIn(0.0, 10.0)
                                        calculatedScore = adjusted
                                    }
                                } catch(_: Exception) { }
                                isAnalyzing = false
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (isAnalyzing) "AI 分析中..." else "计算运动加分")
                }

                Spacer(Modifier.height(8.dp))
                Text("运动加分: ${FormatUtils.doubleToString(calculatedScore, 1)}")
            }
        },
        confirmButton = {
            Button(onClick = {
                val dist = distanceKm.toDoubleOrNull() ?: return@Button
                val dur = (durationMin.toDoubleOrNull() ?: return@Button).toInt() * 60
                onConfirm(dist, dur, avgHr.toIntOrNull(), maxHr.toIntOrNull(),
                    avgPace.ifBlank { null }, avgCadence.toIntOrNull(), maxCadence.toIntOrNull(),
                    if (z1.isNotBlank() || z2.isNotBlank() || z3.isNotBlank() || z4.isNotBlank() || z5.isNotBlank()) {
                        HrZones(z1.toIntOrNull()?:0, z2.toIntOrNull()?:0, z3.toIntOrNull()?:0, z4.toIntOrNull()?:0, z5.toIntOrNull()?:0)
                    } else null, calculatedScore)
            }) { Text("保存") }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("取消") }
        }
    )
}
