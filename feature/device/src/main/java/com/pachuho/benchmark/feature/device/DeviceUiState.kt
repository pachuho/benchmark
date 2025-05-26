package com.pachuho.benchmark.feature.device

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.pachuho.benchmark.core.model.Device
import com.pachuho.benchmark.core.model.Status

@Stable
sealed interface DeviceUiState {
    @Immutable
    data object Loading : DeviceUiState

    @Immutable
    data object Failure : DeviceUiState

    @Immutable
    data class Devices(val devices: List<Device>) : DeviceUiState
}