package com.pachuho.benchmark.feature.device

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.pachuho.benchmark.core.model.ControlField
import com.pachuho.benchmark.core.model.Light
import com.pachuho.benchmark.core.model.LightStatus
import com.pachuho.benchmark.core.model.Plug
import com.pachuho.benchmark.core.model.PlugStatus

internal class DeviceDetailPreviewParameterProvider : PreviewParameterProvider<DeviceDetailUiState> {
    override val values = sequenceOf(
        DeviceDetailUiState.Device(
            device = Plug(
                deviceId = "1",
                productId = "2",
                online = true,
                status = PlugStatus(
                    ControlField(
                        code = "switch_1",
                        value = true
                    )
                )
            )
        ),
        DeviceDetailUiState.Device(
            device = Light(
                deviceId = "2",
                productId = "2",
                online = true,
                status = LightStatus(
                    ControlField(
                        code = "switch_led",
                        value = true
                    )
                )
            )
        )
    )
}