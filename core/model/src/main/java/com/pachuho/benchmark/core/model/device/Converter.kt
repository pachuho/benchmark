package com.pachuho.benchmark.core.model.device

fun <T> getUpdatedDevice(device: Device, controlField: ControlField<T>): Device? {
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

                device.status.motionDetect.code -> {
                    device.copy(
                        status = device.status.copy(
                            motionDetect = ControlField(
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

        is BasicDevice -> {
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

        // TODO
        else -> null
    }
}