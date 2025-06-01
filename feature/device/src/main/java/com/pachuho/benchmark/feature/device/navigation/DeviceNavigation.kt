package com.pachuho.benchmark.feature.device.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pachuho.benchmark.core.model.device.Device
import com.pachuho.benchmark.core.navigation.Route
import com.pachuho.benchmark.core.navigation.composableWithAnimation
import com.pachuho.benchmark.feature.device.detail.PlugDetailRoute
import com.pachuho.benchmark.feature.device.DeviceRoute

fun NavController.navigateDevice(navOptions: NavOptions) {
    navigate(Route.Device, navOptions)
}

fun NavController.navigateDeviceDetail(deviceId: String) {
    navigate("device_detail/$deviceId")
}

fun NavGraphBuilder.deviceNavGraph(
    padding: PaddingValues,
    onShowErrorSnackBar: (throwable: Throwable) -> Unit,
    onClickItem: (Device) -> Unit,
    onBack: () -> Unit
) {
    composable<Route.Device> {
        DeviceRoute(padding, onShowErrorSnackBar, onClickItem)
    }

    composableWithAnimation<Route.DeviceDetail>(
        arguments = listOf(
            navArgument("deviceId") { type = NavType.StringType }
        )
    ) {
        PlugDetailRoute(
            padding = padding,
            onShowErrorSnackBar = onShowErrorSnackBar,
            onBack = onBack
        )
    }
}
