package com.pachuho.benchmark.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.pachuho.benchmark.core.navigation.BenchmarkRoute
import com.pachuho.benchmark.feature.device.navigation.navigateDevice
import com.pachuho.benchmark.feature.device.navigation.navigateDeviceDetail
import com.pachuho.benchmark.feature.login.navigation.navigateLogin

internal class MainNavigator(
    val navController: NavHostController,
) {
    val startDestination = BenchmarkRoute.Login

    fun navigateLogin(isTop: Boolean) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = true
            }
            launchSingleTop = isTop
        }
        navController.navigateLogin(navOptions)
    }

    fun navigateDevice(isTop: Boolean) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = true
            }
            launchSingleTop = isTop
        }
        navController.navigateDevice(navOptions)
    }

    fun navigateDeviceDetail(deviceId: String) {
        navController.navigateDeviceDetail(deviceId)
    }

    private fun popBackStack() {
        navController.popBackStack()
    }

    fun popBackStackIfNotDevice() {
        if (!isSameCurrentDestination<BenchmarkRoute.Device>()) {
            popBackStack()
        }
    }

    private inline fun <reified T : BenchmarkRoute> isSameCurrentDestination(): Boolean {
        return navController.currentDestination?.hasRoute<T>() == true
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
