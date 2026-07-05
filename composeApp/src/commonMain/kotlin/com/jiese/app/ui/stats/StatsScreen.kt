package com.jiese.app.ui.stats

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jiese.app.AppState
import com.jiese.app.FormatUtils
import com.jiese.app.domain.ScoreEngine
import kotlinx.datetime.*
import top.yukonga.miuix.kmp.basic.*

@Composable
fun StatsScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("戒色", "跑步", "睡眠")

    Scaffold(
        topBar = {
            TopAppBar(
                title = "📊 统计数据",
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ScrollableTabBar(
                tabs = tabs,
                selectedIndex = selectedTab,
                onSelectedIndexChanged = { selectedTab = it }
            )
            when (selectedTab) {
                0 -> RelapseStatsTab()
                1 -> RunningStatsTab()
                2 -> SleepStatsTab()
            }
        }
    }
}

@Composable
private fun RelapseStatsTab() {
    val repo = AppState.repository
    val now = Clock.System.now().toEpochMilliseconds()
    val today = ScoreEngine.toDateMillis(now)
    val monthStart = today - 30 * 86400000L
    val allRelapses = remember { repo.getAllRelapses() }
    val monthRelapses = remember { repo.getRelapsesBetween(monthStart, now) }
    val latestRelapse = remember { repo.getLatestRelapse() }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            StatCard("总破戒", "${allRelapses.size} 次")
            StatCard("本月", "${monthRelapses.size} 次")
            StatCard("日均",
                if (allRelapses.size > 0) FormatUtils.floatToString(monthRelapses.size.toFloat() / 30, 1) else "0")
        }
        Spacer(Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                SmallTitle(text = "破戒时间分布")
                Spacer(Modifier.height(8.dp))
                monthRelapses.take(20).forEach { relapse ->
                    val dt = Instant.fromEpochMilliseconds(relapse.timestamp)
                        .toLocalDateTime(TimeZone.currentSystemDefault())
                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                        Text("${dt.monthNumber}/${dt.dayOfMonth} ${dt.hour}:${dt.minute.toString().padStart(2, '0')}")
                        Text(relapse.note ?: "", fontSize = 12.sp)
                    }
                }
                if (monthRelapses.isEmpty()) {
                    Text("本月暂无破戒记录，继续保持 💪")
                }
            }
        }
    }
}

@Composable
private fun RunningStatsTab() {
    val repo = AppState.repository
    val now = Clock.System.now().toEpochMilliseconds()
    val today = ScoreEngine.toDateMillis(now)
    val monthStart = today - 30 * 86400000L
    val stats = remember { repo.getRunningStats(monthStart, now) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            StatCard("本月跑量", "${FormatUtils.doubleToString(stats.first, 1)} km")
            StatCard("本月运动分", "+${stats.second.toInt()}")
            StatCard("本月次数", "${stats.third} 次")
        }
        Spacer(Modifier.height(16.dp))
        RunningTrendChart(repo)
    }
}

@Composable
private fun SleepStatsTab() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                SmallTitle(text = "睡眠分析")
                Spacer(Modifier.height(8.dp))
                Text("本周睡眠趋势数据即将上线")
                Text("通过 设置 → Health Connect 连接睡眠数据")
            }
        }
    }
}

@Composable
private fun StatCard(label: String, value: String) {
    Card(modifier = Modifier.width(110.dp)) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, fontSize = 20.sp)
            Text(label, fontSize = 12.sp)
        }
    }
}

@Composable
private fun RunningTrendChart(repo: com.jiese.app.data.repository.AppRepository) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            SmallTitle(text = "近 7 天跑量")
            Spacer(Modifier.height(12.dp))

            val now = Clock.System.now().toEpochMilliseconds()
            val today = ScoreEngine.toDateMillis(now)
            val data = (6 downTo 0).map { daysAgo ->
                val start = today - daysAgo * 86400000L
                val end = start + 86400000L
                repo.getRunningStats(start, end).first
            }
            val maxVal = data.maxOrNull()?.coerceAtLeast(1.0) ?: 1.0

            data.forEachIndexed { index, value ->
                val label = when (index) {
                    6 -> "今天"
                    5 -> "昨天"
                    else -> "${index}天前"
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(label, modifier = Modifier.width(50.dp), fontSize = 12.sp)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(16.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth((value / maxVal).toFloat())
                        ) {}
                    }
                    Text(" ${FormatUtils.doubleToString(value, 1)}km", fontSize = 12.sp)
                }
            }
        }
    }
}
