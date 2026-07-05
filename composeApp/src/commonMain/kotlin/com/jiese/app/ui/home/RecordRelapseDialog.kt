package com.jiese.app.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.*
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun RecordRelapseDialog(
    onDismiss: () -> Unit,
    onConfirm: (String?) -> Unit
) {
    var note by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "❌ 记录破戒",
                style = MiuixTheme.textStyles.title2
            )
        },
        text = {
            Column {
                TextField(
                    value = note,
                    onValueChange = { note = it },
                    label = "破戒原因/情境 (可选)",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(note.ifBlank { null }) }) {
                Text("确认")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("取消")
            }
        }
    )
}
