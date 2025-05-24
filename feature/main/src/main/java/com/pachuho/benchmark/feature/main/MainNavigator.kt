package com.pachuho.benchmark.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pachuho.benchmark.core.navigation.Route
import com.pachuho.benchmark.feature.device.navigation.navigateDevice
import com.pachuho.benchmark.feature.login.navigation.navigateLogin

internal class MainNavigator(
    val navController: NavHostController,
) {
    val startDestination = Route.Login

    fun navigateLogin() {
        navController.navigateLogin()
    }

    fun navigateDevice() {
        navController.navigateDevice()
    }

    fun navigateDeviceDetail() {
        // TODO
    }

    private fun popBackStack() {
        navController.popBackStack()
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
