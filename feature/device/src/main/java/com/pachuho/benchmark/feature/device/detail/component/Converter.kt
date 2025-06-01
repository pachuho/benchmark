package com.pachuho.benchmark.feature.device.detail.component

import com.pachuho.benchmark.core.model.device.CameraDevice
import com.pachuho.benchmark.core.model.device.ControlField
import com.pachuho.benchmark.core.model.device.Device
import com.pachuho.benchmark.core.model.device.LightDevice
import com.pachuho.benchmark.core.model.device.PlugDevice

internal fun <T> getUpdatedDevice(device: Device, controlField: ControlField<T>): Device? {
    return when(device) {
        is PlugDevice -> {
            when(controlField.code) {
                device.status.switch.code -> {
                    device.copy(
                        status = device.status.copy(
                            switch = ControlField(
                                code = controlField.code,
                                value = controlField.value as Boolean
                            )
                        )
                    )
                }

                else -> null
            }
        }
        is LightDevice -> {
            when(controlField.code) {
                device.status.switch.code -> {
                    device.copy(
                        status = device.status.copy(
                            switch = ControlField(
                                code = controlField.code,
                                value = controlField.value as Boolean
                            )
                        )
                    )
                }
                device.status.bright.code -> {
                    device.copy(
                        status = device.status.copy(
                            bright = ControlField(
                                code = controlField.code,
                                value = controlField.value as Int
                            )
                        )
                    )
                }
                else -> null
            }
        }
        is CameraDevice -> {
            when(controlField.code) {
                device.status.indicator.code -> {
                    device.copy(
                        status = device.status.copy(
                            indicator = ControlField(
                                code = controlField.code,
                                value = controlField.value as Boolean
                            )
                        )
                    )
                }

                device.status.privateMode.code -> {
                    device.copy(
                        status = device.status.copy(
                            privateMode = ControlField(
                                code = controlField.code,
                                value = controlField.value as Boolean
                            )
                        )
                    )
                }

                // TODO
                else -> null
            }
        }

        // TODO
        else -> null
    }
}