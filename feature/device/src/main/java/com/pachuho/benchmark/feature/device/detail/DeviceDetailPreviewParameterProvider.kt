package com.pachuho.benchmark.feature.device.detail

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.pachuho.benchmark.core.model.device.CameraDevice
import com.pachuho.benchmark.core.model.device.CameraStatus
import com.pachuho.benchmark.core.model.device.ControlField
import com.pachuho.benchmark.core.model.device.LightDevice
import com.pachuho.benchmark.core.model.device.LightStatus
import com.pachuho.benchmark.core.model.device.PlugDevice
import com.pachuho.benchmark.core.model.device.PlugStatus

internal class DeviceDetailPreviewParameterProvider : PreviewParameterProvider<DeviceDetailUiState> {
    override val values = sequenceOf(
        DeviceDetailUiState.Device(
            device = PlugDevice(
                deviceId = "s8616242a58d13cc66xszg",
                productId = "uxjr57hvapakd0io",
                name = "플러그 Mini 2",
                online = true,
                status = PlugStatus(
                    ControlField(
                        code = "switch",
                        value = true
                    )
                )
            )
        ),
        DeviceDetailUiState.Device(
            device = LightDevice(
                name = "무드등",
                deviceId = "52868143a4e57c1e4112",
                productId = "mhf0rqd7uuvz6hf8",
                online = true,
                status = LightStatus(
                    switch = ControlField(
                        code = "switch",
                        value = true
                    ),
                    bright = ControlField(
                        code = "bright",
                        value = 1
                    ),
                    mode = ControlField(
                        code = "mode",
                        value = "white"
                    )
                )
            )
        ),
        DeviceDetailUiState.Device(
            device = CameraDevice(
                name = "홈카메라 Pro+",
                deviceId = "s856571db9d4ecc43e4ijg",
                productId = "3cwbcqiz8qixphvu",
                online = true,
                status = CameraStatus(
                    indicator = ControlField(
                        code = "switch",
                        value = true
                    ),
                    privateMode = ControlField(
                        code = "bright",
                        value = true
                    )
                )
            )
        )
    )
}