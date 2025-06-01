package com.pachuho.benchmark.feature.device.detail.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pachuho.benchmark.core.designsystem.theme.BenchmarkTheme
import com.pachuho.benchmark.core.designsystem.theme.Blue050
import com.pachuho.benchmark.core.model.device.ControlField
import com.pachuho.benchmark.core.model.device.LightDevice
import com.pachuho.benchmark.core.model.device.LightStatus
import com.pachuho.benchmark.core.ui.DevicePreviews
import com.pachuho.benchmark.core.ui.clickableWithoutEffect
import kotlin.math.roundToInt

@Composable
internal fun LightComponent(
    device: LightDevice,
    onControl: (ControlField<*>) -> Unit
) {
    val controlColor = if (device.getDirectControlStatus()) Color.Unspecified else Color.Gray
    var sliderValue by remember { mutableFloatStateOf(device.status.bright.value.toFloat()) }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val minSide = minOf(maxWidth, maxHeight)
        val iconSize = minSide / 3

        Column(
            modifier = Modifier.width(maxWidth / 5 * 4),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .width(iconSize)
                    .height(iconSize)
                    .clickableWithoutEffect {
                        onControl(device.reverseSwitch())
                    },
                painter = painterResource(com.pachuho.benchmark.core.model.R.drawable.ic_light),
                contentDescription = null,
                tint = controlColor
            )

            Text(
                text = device.getControlText(),
                style = BenchmarkTheme.typography.titleMediumR,
            )

            AnimatedVisibility(
                visible = device.status.switch.value,
            ) {
                Column(
                    modifier = Modifier.padding(top = 50.dp)
                ) {
                    Slider(
                        value = sliderValue,
                        onValueChange = {
                            sliderValue = it
                        },
                        onValueChangeFinished = {
                            onControl(device.updateBright(sliderValue.roundToInt()))
                        },
                        valueRange = 1f..100f,
                        steps = 0
                    )

                    Text(
                        text = device.getControlText(),
                        style = BenchmarkTheme.typography.labelMediumR,
                    )
                }
            }
            if (!device.status.switch.value) {
                Spacer(Modifier.height(64.dp))
            }
        }
    }
}

@DevicePreviews()
@Composable
private fun LightComponentPreview() {
    BenchmarkTheme {
        LightComponent(
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
                        value = 50
                    ),
                    mode = ControlField(
                        code = "workMode",
                        value = "white"
                    )
                )
            ),
            onControl = {}
        )
    }
}