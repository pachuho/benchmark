package com.pachuho.benchmark.feature.device

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.pachuho.benchmark.core.model.Device

@Stable
sealed interface DeviceUiState {
    @Immutable
    data object Error : DeviceUiState

    @Immutable
    data class Devices(val devices: List<Device>) : DeviceUiState
}