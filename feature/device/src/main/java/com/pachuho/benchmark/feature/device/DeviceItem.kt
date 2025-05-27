package com.pachuho.benchmark.feature.device

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pachuho.benchmark.core.designsystem.theme.BenchmarkTheme
import com.pachuho.benchmark.core.designsystem.theme.Blue700
import com.pachuho.benchmark.core.model.Device
import com.pachuho.benchmark.core.model.Status
import com.pachuho.benchmark.core.ui.clickableWithoutEffect

@Composable
internal fun DeviceItem(
    modifier: Modifier = Modifier,
    device: Device,
    color: Color = MaterialTheme.colorScheme.background,
    onClickItem: (Device) -> Unit,
    onClickControl: (Device) -> Unit
) {
    val backgroundColor = if (device.online) color else Color.LightGray
    val controlColor = if (device.status.switch) Blue700 else Color.Gray

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
                    }
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
                    painter = painterResource(R.drawable.ic_plug),
                    contentDescription = null,
                    tint = Color.Unspecified
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(48.dp)
                        .clickableWithoutEffect { onClickControl(device) },
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
                    text = "플러그 Mini",
                    style = BenchmarkTheme.typography.labelMediumR,
                )
            }
        }
    }
}

@Preview(widthDp = 200)
@Composable
private fun DeviceItemPreviewOnline() {
    BenchmarkTheme {
        DeviceItem(
            device = Device(
                deviceId = "1",
                productId = "2",
                online = true,
                status = Status(
                    switch = true
                )
            ),
            onClickItem = {},
            onClickControl = {}
        )
    }
}

@Preview(widthDp = 200)
@Composable
private fun DeviceItemPreviewOffline() {
    BenchmarkTheme {
        DeviceItem(
            device = Device(
                deviceId = "1",
                productId = "2",
                online = false,
                status = Status(
                    switch = false
                )
            ),
            onClickItem = {},
            onClickControl = {}
        )
    }
}