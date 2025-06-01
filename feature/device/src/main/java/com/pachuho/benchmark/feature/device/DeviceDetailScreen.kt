package com.pachuho.benchmark.feature.device

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pachuho.benchmark.core.designsystem.component.BenchmarkLoading
import com.pachuho.benchmark.core.designsystem.component.BenchmarkTopAppBar
import com.pachuho.benchmark.core.designsystem.component.TopAppBarNavigationType
import com.pachuho.benchmark.core.designsystem.theme.BenchmarkTheme
import com.pachuho.benchmark.core.designsystem.theme.Blue050
import com.pachuho.benchmark.core.designsystem.theme.Blue700
import com.pachuho.benchmark.core.model.device.BasicDevice
import com.pachuho.benchmark.core.model.device.Device
import com.pachuho.benchmark.core.model.device.Light
import com.pachuho.benchmark.core.model.device.Plug
import com.pachuho.benchmark.core.ui.DevicePreviews
import com.pachuho.benchmark.core.ui.clickableWithoutEffect
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun PlugDetailRoute(
    padding: PaddingValues,
    onShowErrorSnackBar: (throwable: Throwable) -> Unit,
    onBack: () -> Unit,
    viewModel: DeviceDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.errorFlow.collectLatest { throwable -> onShowErrorSnackBar(throwable) }
    }

    DeviceDetailScreen(
        uiState = uiState,
        padding = padding,
        onBack = onBack,
        onControl = { viewModel.toggleDevice() }
    )
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun DeviceDetailScreen(
    uiState: DeviceDetailUiState,
    padding: PaddingValues,
    onBack: () -> Unit,
    onControl: (Device) -> Unit,
) {
    Box(
        modifier = Modifier
            .background(Blue050)
            .padding(padding)
    ) {
        when(uiState) {
            is DeviceDetailUiState.Device -> {
                val device = uiState.device
                val controlColor = if (device.getDirectControlStatus()) Blue700 else Color.Gray

                BenchmarkTopAppBar(
                    modifier = Modifier.align(Alignment.TopCenter),
                    title = device.name,
                    navigationType = TopAppBarNavigationType.Back,
                    onNavigationClick = onBack
                )

                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    val minSide = minOf(maxWidth, maxHeight)
                    val iconSize = minSide / 3

                    Column(
                        verticalArrangement = Arrangement.spacedBy(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier
                                .width(iconSize)
                                .height(iconSize)
                                .background(controlColor, CircleShape)
                                .clickableWithoutEffect { onControl(uiState.device) },
                            painter = painterResource(R.drawable.ic_power),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )

                        Text(
                            text = device.getControlText(),
                            style = BenchmarkTheme.typography.titleMediumR,
                        )
                    }
                }
            }
            DeviceDetailUiState.Error -> {
                BenchmarkTopAppBar(
                    modifier = Modifier.align(Alignment.TopCenter),
                    title = "Device",
                    navigationType = TopAppBarNavigationType.Back,
                    onNavigationClick = onBack
                )

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier.size(50.dp),
                        painter = painterResource(R.drawable.ic_error),
                        contentDescription = null,
                        tint = Blue700
                    )

                    Text(
                        text = stringResource(R.string.failure_get_device),
                        style = BenchmarkTheme.typography.titleMediumB,
                    )
                }
            }
            DeviceDetailUiState.Loading -> BenchmarkLoading()
        }
    }
}

@DevicePreviews
@Composable
private fun PlugDetailScreenPreview(
    @PreviewParameter(DeviceDetailPreviewParameterProvider::class) uiState: DeviceDetailUiState
) {
    BenchmarkTheme {
        DeviceDetailScreen(
            uiState = uiState,
            padding = PaddingValues(),
            onBack = {},
            onControl = {}
        )
    }
}