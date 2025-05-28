package com.pachuho.benchmark.feature.device.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pachuho.benchmark.core.model.Device
import com.pachuho.benchmark.core.navigation.Route
import com.pachuho.benchmark.feature.device.DeviceRoute

fun NavController.navigateDevice() {
    navigate(Route.Device)
}

fun NavGraphBuilder.deviceNavGraph(
    padding: PaddingValues,
    onShowErrorSnackBar: (message: Int) -> Unit,
    onClickItem: (Device) -> Unit
) {
    composable<Route.Device> {
        DeviceRoute(padding, onShowErrorSnackBar, onClickItem)
    }
}
