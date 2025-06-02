package com.pachuho.benchmark.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pachuho.benchmark.core.designsystem.theme.BenchmarkTheme

@Composable
fun BenchmarkDialog(
    title: String,
    body: String,
    confirmText: String = "확인",
    onDismiss: () -> Unit,
    onConfirm: (() -> Unit)? = null
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White, shape = MaterialTheme.shapes.medium)
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = title,
                    style = BenchmarkTheme.typography.titleLargeR,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = body,
                    style = BenchmarkTheme.typography.titleMediumR,
                )
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = {
                        onConfirm?.invoke()
                        onDismiss()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(12.dp),
                        text = confirmText
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun BenchmarkDialogPreview() {
    BenchmarkTheme {
        BenchmarkDialog(
            title = "알림",
            body = "홈 카메라에서 동작이 감지되었습니다. ",
            onDismiss = {},
            onConfirm = {},
        )
    }
}