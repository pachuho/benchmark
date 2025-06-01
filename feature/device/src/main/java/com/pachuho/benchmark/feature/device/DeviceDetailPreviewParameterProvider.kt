package com.pachuho.benchmark.feature.device

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.pachuho.benchmark.core.model.device.ControlField
import com.pachuho.benchmark.core.model.device.Light
import com.pachuho.benchmark.core.model.device.LightStatus
import com.pachuho.benchmark.core.model.device.Plug
import com.pachuho.benchmark.core.model.device.PlugStatus

internal class DeviceDetailPreviewParameterProvider : PreviewParameterProvider<DeviceDetailUiState> {
    override val values = sequenceOf(
        DeviceDetailUiState.Device(
            device = Plug(
                name = "플러그_미니 1",
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
                name = "무드등 1",
                deviceId = "2",
                productId = "2",
                online = true,
                status = LightStatus(
                    switch = ControlField(
                        code = "switchLed",
                        value = true
                    ),
                    bright = ControlField(
                        code = "brightValue",
                        value = 1
                    ),
                    mode = ControlField(
                        code = "workMode",
                        value = "white"
                    )
                )
            )
        )
    )
}