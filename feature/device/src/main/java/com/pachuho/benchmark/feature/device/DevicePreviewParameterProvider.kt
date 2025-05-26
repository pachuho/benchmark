package com.pachuho.benchmark.feature.device

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.pachuho.benchmark.core.model.Device
import com.pachuho.benchmark.core.model.Status

internal class DevicePreviewParameterProvider : PreviewParameterProvider<DeviceUiState> {
    override val values = sequenceOf(
        DeviceUiState.Devices(
            devices = listOf(
                Device(
                    deviceId = "1",
                    productId = "2",
                    online = true,
                    status = Status(
                        switch = true
                    )
                ),
                Device(
                    deviceId = "2",
                    productId = "2",
                    online = true,
                    status = Status(
                        switch = true
                    )
                ),
                Device(
                    deviceId = "3",
                    productId = "2",
                    online = true,
                    status = Status(
                        switch = true
                    )
                ),
                Device(
                    deviceId = "4",
                    productId = "2",
                    online = true,
                    status = Status(
                        switch = true
                    )
                )
            )
        ),
        DeviceUiState.Loading,
        DeviceUiState.Error
    )
}