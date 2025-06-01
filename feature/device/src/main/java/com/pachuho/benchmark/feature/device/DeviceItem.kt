package com.pachuho.benchmark.feature.device

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pachuho.benchmark.core.designsystem.theme.BenchmarkTheme
import com.pachuho.benchmark.core.designsystem.theme.Blue700
import com.pachuho.benchmark.core.model.device.BasicDevice
import com.pachuho.benchmark.core.model.device.CameraDevice
import com.pachuho.benchmark.core.model.device.ControlField
import com.pachuho.benchmark.core.model.device.Device
import com.pachuho.benchmark.core.model.device.LightDevice
import com.pachuho.benchmark.core.model.device.LightStatus
import com.pachuho.benchmark.core.model.device.PlugDevice
import com.pachuho.benchmark.core.model.device.PlugStatus
import com.pachuho.benchmark.core.ui.clickableWithoutEffect

@Composable
internal fun DeviceItem(
    modifier: Modifier = Modifier,
    device: Device,
    color: Color = MaterialTheme.colorScheme.background,
    onClickItem: (Device) -> Unit,
    onControl: (String, ControlField<*>) -> Unit,
) {
    val backgroundColor = if (device.online) color else Color.LightGray
    val controlColor = if (device.getDirectControlStatus()) Blue700 else Color.Gray
    val interactionSource = remember { MutableInteractionSource() }

    BoxWithConstraints(
        modifier = modifier.fillMaxWidth()
    ) {
        val width = this.maxWidth
        val height = width * 5f / 7f // 가로:세로 = 7:5

        Surface(
            modifier = Modifier
                .width(width)
                .height(height)
                .clip(RoundedCornerShape(8.dp))
                .clickable(
                    enabled = device.online,
                    onClick = {
                        onClickItem(device)
                    },
                    interactionSource = interactionSource,
                    indication = null
                ),
            color = backgroundColor,
            shape = RoundedCornerShape(8.dp),
            shadowElevation = 2.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .size(width.div(4)),
                    painter = painterResource(device.icon),
                    contentDescription = null,
                    tint = Color.Unspecified
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(48.dp)
                        .clickableWithoutEffect(
                            enabled = device.online,
                        ) {
                            onControl(
                                device.deviceId,
                                when(device) {
                                    is PlugDevice -> device.reverseSwitch()
                                    is LightDevice -> device.reverseSwitch()
                                    is CameraDevice -> device.reverseIndicator()
                                    is BasicDevice -> device.reverseSwitch()
                                    else -> return@clickableWithoutEffect
                                }
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier
                            .size(width.div(7))
                            .background(controlColor, CircleShape)
                            .padding(4.dp),
                        painter = painterResource(R.drawable.ic_power),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(top = 20.dp),
                    text = device.name,
                    style = BenchmarkTheme.typography.labelMediumR,
                )
            }
        }
    }
}

@Preview(widthDp = 200)
@Composable
private fun DeviceItemPreviewPlug() {
    BenchmarkTheme {
        DeviceItem(
            device = PlugDevice(
                name = "플러그",
                deviceId = "1",
                productId = "2",
                online = true,
                status = PlugStatus(
                    ControlField(
                        code = "switch_1",
                        value = true
                    )
                )
            ),
            onClickItem = {},
            onControl = { deviceId, controlField ->

            }
        )
    }
}

@Preview(widthDp = 200)
@Composable
private fun DeviceItemPreviewLight() {
    BenchmarkTheme {
        DeviceItem(
            device = LightDevice(
                name = "무드등",
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
            ),
            onClickItem = {},
            onControl = { deviceId, controlField ->

            }
        )
    }
}