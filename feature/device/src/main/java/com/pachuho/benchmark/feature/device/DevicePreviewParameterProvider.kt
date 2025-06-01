package com.pachuho.benchmark.feature.device

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.pachuho.benchmark.core.model.device.ControlField
import com.pachuho.benchmark.core.model.device.PlugDevice
import com.pachuho.benchmark.core.model.device.PlugStatus

internal class DevicePreviewParameterProvider : PreviewParameterProvider<DeviceUiState> {
    override val values = sequenceOf(
        DeviceUiState.Devices(
            devices = List(50) { index ->
                PlugDevice(
                    name = "플러그_미니 ${(index + 1)}",
                    deviceId = (index + 1).toString(),
                    productId = "2",
                    online = true,
                    status = PlugStatus(
                        ControlField(
                            code = "switch_1",
                            value = true
                        )
                    )
                )
            }
        ),
        DeviceUiState.Error
    )
}

