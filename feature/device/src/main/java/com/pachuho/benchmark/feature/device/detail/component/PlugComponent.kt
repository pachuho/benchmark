package com.pachuho.benchmark.feature.device.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pachuho.benchmark.core.designsystem.theme.BenchmarkTheme
import com.pachuho.benchmark.core.designsystem.theme.Blue700
import com.pachuho.benchmark.core.model.device.ControlField
import com.pachuho.benchmark.core.model.device.PlugDevice
import com.pachuho.benchmark.core.model.device.PlugStatus
import com.pachuho.benchmark.core.ui.clickableWithoutEffect
import com.pachuho.benchmark.feature.device.R

@Composable
internal fun PlugComponent(
    device: PlugDevice,
    onControl: (ControlField<*>) -> Unit
) {
    val controlColor = if (device.getDirectControlStatus()) Blue700 else Color.Gray

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val minSide = minOf(maxWidth, maxHeight)
        val iconSize = minSide / 3

        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .width(iconSize)
                    .height(iconSize)
                    .background(controlColor, CircleShape)
                    .clickableWithoutEffect {
                        onControl(device.reverseSwitch())
                    },
                painter = painterResource(R.drawable.ic_power),
                contentDescription = null,
                tint = Color.Unspecified
            )

            Text(
                text = device.getControlText(),
                style = BenchmarkTheme.typography.titleMediumR,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PlugComponentPreview() {
    PlugComponent(
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
        ),
        onControl = {}
    )
}