package com.pachuho.benchmark.feature.device

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.pachuho.benchmark.core.model.Device
import com.pachuho.benchmark.core.model.Status

internal class DeviceDetailPreviewParameterProvider : PreviewParameterProvider<DeviceDetailUiState> {
    override val values = sequenceOf(
        DeviceDetailUiState.Device(
            device =
                Device(
                    deviceId = "1",
                    productId = "2",
                    online = true,
                    status = Status(
                        switch = true
                    )
                )
        )
    )
}