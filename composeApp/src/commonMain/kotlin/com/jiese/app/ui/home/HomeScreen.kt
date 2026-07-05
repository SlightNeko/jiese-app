package com.jiese.app.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jiese.app.AppState
import com.jiese.app.data.model.RelapseRecord
import kotlinx.datetime.Clock
import top.yukonga.miuix.kmp.basic.*

@Composable
fun HomeScreen() {
    val repo = AppState.repository
    val engine = AppState.scoreEngine
    var latestRelapse by remember { mutableStateOf<RelapseRecord?>(null) }
    var totalRelapses by remember { mutableStateOf(0L) }
    var totalScore by remember { mutableStateOf(0.0) }
    var showRelapseDialog by remember { mutableStateOf(false) }
    var showRunningDialog by remember { mutableStateOf(false) }
    var longestStreak by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        latestRelapse = repo.getLatestRelapse()
        val now = Clock.System.now().toEpochMilliseconds()
        totalRelapses = repo.getAllRelapses().size.toLong()
        totalScore = repo.getSetting("total_score")?.toDoubleOrNull() ?: 0.0
        longestStreak = repo.getSetting("max_streak")?.toIntOrNull() ?: 0
    }

    val streak: Int = remember(latestRelapse?.timestamp) {
        engine.calculateCurrentStreak(latestRelapse?.timestamp, Clock.System.now().toEpochMilliseconds())
    }

    if (streak > longestStreak) {
        LaunchedEffect(streak) {
            repo.setSetting("max_streak", streak.toString())
            longestStreak = streak
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))
        StreakCard(streak = streak, score = totalScore)
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = { showRelapseDialog = true },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("❌ 记录破戒", fontSize = 18.sp)
        }

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = { showRunningDialog = true },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("🏃 记录跑步", fontSize = 18.sp)
        }

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(label = "最长", value = "${longestStreak}天")
            StatItem(label = "累计破戒", value = "${totalRelapses}次")
            StatItem(label = "总积分", value = "${totalScore.toInt()}")
        }

        Spacer(Modifier.height(16.dp))
        MilestoneProgress(streak = streak)
    }

    if (showRelapseDialog) {
        RecordRelapseDialog(
            onDismiss = { showRelapseDialog = false },
            onConfirm = { note ->
                val now = Clock.System.now().toEpochMilliseconds()
                val penalty = engine.calculateRelapsePenalty(
                    engine.daysSinceLastRelapse(latestRelapse?.timestamp, now)
                )
                totalScore = totalScore + penalty
                totalRelapses++
                repo.setSetting("total_score", totalScore.toString())
                repo.insertRelapse(now, note)
                latestRelapse = repo.getLatestRelapse()
                showRelapseDialog = false
            }
        )
    }
}

@Composable
private fun StreakCard(streak: Int, score: Double) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SmallTitle(text = "🔥 戒色连续 $streak 天")
            Spacer(Modifier.height(8.dp))
            Text(
                text = "综合健康分: ${score.toInt()}",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(label, fontSize = 12.sp)
    }
}

@Composable
private fun MilestoneProgress(streak: Int) {
    val milestones = listOf(7, 14, 30, 90, 365)
    val nextMilestone = milestones.firstOrNull { it > streak } ?: 365
    val progress = (streak.toFloat() / nextMilestone).coerceIn(0f, 1f)
    val bonus = when (nextMilestone) {
        7 -> 5; 14 -> 10; 30 -> 20; 90 -> 50; else -> 100
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text("下个里程碑: ${nextMilestone}天 (+${bonus}分)")
        Spacer(Modifier.height(4.dp))
        Card(modifier = Modifier.fillMaxWidth().height(8.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .fillMaxHeight()
            )
        }
    }
}
