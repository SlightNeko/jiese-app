package com.jiese.app.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jiese.app.AppState
import com.jiese.app.data.model.KEY_API_ENDPOINT
import com.jiese.app.data.model.KEY_API_KEY
import com.jiese.app.data.model.KEY_MODEL_NAME
import top.yukonga.miuix.kmp.basic.*

@Composable
fun SettingsScreen() {
    val repo = AppState.repository
    var apiEndpoint by remember { mutableStateOf("") }
    var apiKey by remember { mutableStateOf("") }
    var modelName by remember { mutableStateOf("gpt-4o-mini") }

    LaunchedEffect(Unit) {
        apiEndpoint = repo.getSetting(KEY_API_ENDPOINT) ?: ""
        apiKey = repo.getSetting(KEY_API_KEY) ?: ""
        modelName = repo.getSetting(KEY_MODEL_NAME) ?: "gpt-4o-mini"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = "⚙️ 设置",
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            SmallTitle(text = "AI 分析配置")
            Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    TextField(
                        value = apiEndpoint,
                        onValueChange = { apiEndpoint = it; repo.setSetting(KEY_API_ENDPOINT, it) },
                        label = "API 地址",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))
                    TextField(
                        value = apiKey,
                        onValueChange = { apiKey = it; repo.setSetting(KEY_API_KEY, it) },
                        label = "API Key",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))
                    TextField(
                        value = modelName,
                        onValueChange = { modelName = it; repo.setSetting(KEY_MODEL_NAME, it) },
                        label = "模型名称",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "兼容 OpenAI API 格式即可，如:\n" +
                        "https://api.openai.com/v1/chat/completions\n" +
                        "https://api.deepseek.com/v1/chat/completions"
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
            SmallTitle(text = "数据管理")
            Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Button(
                        onClick = { exportData() },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("导出数据") }
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = { /* Import dialog */ },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("导入数据") }
                }
            }

            Spacer(Modifier.height(16.dp))
            SmallTitle(text = "关于")
            Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                        Text("版本")
                        Text("1.0.0")
                    }
                }
            }
        }
    }
}

private fun exportData() {
}
