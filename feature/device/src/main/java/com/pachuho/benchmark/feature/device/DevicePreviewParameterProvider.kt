package com.pachuho.benchmark.feature.device

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.pachuho.benchmark.core.model.ControlField
import com.pachuho.benchmark.core.model.Plug
import com.pachuho.benchmark.core.model.PlugStatus

internal class DevicePreviewParameterProvider : PreviewParameterProvider<DeviceUiState> {
    override val values = sequenceOf(
        DeviceUiState.Devices(
            devices = List(50) { index ->
                Plug(
                    deviceId = (index + 1).toString(),
                    productId = "2",
                    online = true,
                    status = PlugStatus(
                        ControlField(
                            code = "1",
                            value = true
                        )
                    )
                )
            },
        )
    )
}

