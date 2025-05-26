package com.pachuho.benchmark.feature.device

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.tooling.preview.Preview
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
import com.pachuho.benchmark.core.model.Device
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DeviceRoute(
    padding: PaddingValues,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    onClickItem: (Device) -> Unit,
    viewModel: DeviceViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.errorFlow.collectLatest { throwable -> onShowErrorSnackBar(throwable) }
    }

    DeviceScreen(
        uiState = uiState,
        padding = padding,
        onClickItem = onClickItem,
        onClickControl = {}
    )
}

@Composable
fun DeviceScreen(
    uiState: DeviceUiState,
    padding: PaddingValues,
    onClickItem: (Device) -> Unit,
    onClickControl: (Device) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Blue050)
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BenchmarkTopAppBar(
            titleRes = R.string.device_list,
            navigationType = TopAppBarNavigationType.None,
        )

        when(uiState) {
            is DeviceUiState.Devices -> {
                LazyVerticalGrid(
                    modifier = Modifier.background(color = Blue050),
                    columns = GridCells.Adaptive(minSize = 150.dp),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = uiState.devices,
                        key = { device ->
                            device.deviceId
                        }
                    ) { device ->
                        DeviceItem(
                            device = device,
                            color = Color.White,
                            onClickItem = { onClickItem(device) },
                            onClickControl = { onClickControl(device) }
                        )
                    }
                }
            }
            is  DeviceUiState.Loading -> {
                BenchmarkLoading()
            }

            is  DeviceUiState.Failure -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
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
        }
    }
}

@Preview
@Composable
private fun DeviceScreenPreview(
    @PreviewParameter(DevicePreviewParameterProvider::class) uiState: DeviceUiState,
) {
    BenchmarkTheme {
        DeviceScreen(
            uiState = uiState,
            padding = PaddingValues(),
            onClickItem = {},
            onClickControl = {}
        )
    }
}