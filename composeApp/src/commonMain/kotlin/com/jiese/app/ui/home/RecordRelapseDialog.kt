package com.jiese.app.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yukonga.miuix.kmp.basic.*
import top.yukonga.miuix.kmp.utils.MiuixPopupWindowUtil

@Composable
fun RecordRelapseDialog(
    onDismiss: () -> Unit,
    onConfirm: (String?) -> Unit
) {
    var note by remember { mutableStateOf("") }

    MiuixPopupWindowUtil(
        popup = {
            Card(modifier = Modifier.width(320.dp).padding(16.dp)) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    SmallTitle(text = "❌ 记录破戒", fontSize = 20.sp)
                    Spacer(Modifier.height(16.dp))
                    TextField(
                        value = note,
                        onValueChange = { note = it },
                        label = "破戒原因/情境 (可选)",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(onClick = onDismiss) {
                            Text("取消")
                        }
                        Button(onClick = { onConfirm(note.ifBlank { null }) }) {
                            Text("确认")
                        }
                    }
                }
            }
        },
        onDismissRequest = onDismiss
    )
}
