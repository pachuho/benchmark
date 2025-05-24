package com.pachuho.benchmark.feature.device.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pachuho.benchmark.core.designsystem.theme.BenchmarkTheme
import com.pachuho.benchmark.core.designsystem.theme.DeepBlue

@Composable
fun DeviceRoute(
    padding: PaddingValues,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    DeviceScreen(
        padding = padding,
        onShowErrorSnackBar = onShowErrorSnackBar
    )
}

@Composable
fun DeviceScreen(
    padding: PaddingValues,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(padding)
        .background(color = DeepBlue)
    )
}

@Preview
@Composable
private fun DeviceScreenPreview() {
    BenchmarkTheme {
        DeviceScreen(
            padding = PaddingValues(),
            onShowErrorSnackBar = { }
        )
    }
}