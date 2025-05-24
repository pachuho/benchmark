package com.pachuho.benchmark.feature.device.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pachuho.benchmark.core.navigation.Route

fun NavController.navigateDevice() {
    navigate(Route.Device)
}

fun NavGraphBuilder.deviceNavGraph(
    padding: PaddingValues,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    composable<Route.Device> {
        DeviceRoute(padding, onShowErrorSnackBar)
    }
}
