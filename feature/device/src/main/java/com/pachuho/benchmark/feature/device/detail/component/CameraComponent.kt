package com.pachuho.benchmark.feature.device.detail.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pachuho.benchmark.core.designsystem.theme.BenchmarkTheme
import com.pachuho.benchmark.core.designsystem.theme.Blue700
import com.pachuho.benchmark.core.model.device.CameraDevice
import com.pachuho.benchmark.core.model.device.CameraStatus
import com.pachuho.benchmark.core.model.device.ControlField
import com.pachuho.benchmark.core.model.device.PlugDevice
import com.pachuho.benchmark.core.model.device.PlugStatus
import com.pachuho.benchmark.core.ui.clickableWithoutEffect
import com.pachuho.benchmark.feature.device.R

@Composable
internal fun CameraComponent(
    device: CameraDevice,
    onControl: (ControlField<*>) -> Unit
) {
    var checkIndicator by remember { mutableStateOf(device.status.indicator.value) }
    var checkPrivateMode by remember { mutableStateOf(device.status.privateMode.value) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StatusComponent(
            id = R.string.indicator,
            checked = checkIndicator,
            onCheckedChange = {
                checkIndicator = it
                onControl(device.reverseIndicator())
            }
        )

        StatusComponent(
            id = R.string.private_mode,
            checked = checkPrivateMode,
            onCheckedChange = {
                checkPrivateMode = it
                onControl(device.reversePrivateMode())
            }
        )
    }
}

@Composable
private fun StatusComponent(
    @StringRes id: Int,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 6.dp,
                horizontal = 24.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id),
            style = BenchmarkTheme.typography.titleMediumR,
        )

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
    HorizontalDivider(thickness = 1.dp)
}

@Preview(showBackground = true)
@Composable
private fun CameraComponentPreview() {
    BenchmarkTheme {
        CameraComponent(
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
            ),
            onControl = {}
        )
    }
}