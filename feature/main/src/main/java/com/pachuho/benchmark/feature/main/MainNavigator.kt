package com.pachuho.benchmark.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.pachuho.benchmark.core.navigation.MainTabRoute
import com.pachuho.benchmark.core.navigation.Route
import com.pachuho.benchmark.feature.device.navigation.navigateDevice
import com.pachuho.benchmark.feature.login.navigation.navigateLogin

internal class MainNavigator(
    val navController: NavHostController,
) {
    val startDestination = Route.Login

    fun navigate(route: Route, isTop: Boolean) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = true
            }
            launchSingleTop = isTop
        }

        when(route) {
            Route.Login -> navController.navigateLogin(navOptions)
            Route.Device -> navController.navigateDevice(navOptions)
            else -> TODO()
        }


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
