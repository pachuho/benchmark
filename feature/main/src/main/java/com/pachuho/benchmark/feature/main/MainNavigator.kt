package com.pachuho.benchmark.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.pachuho.benchmark.core.navigation.Route
import com.pachuho.benchmark.feature.login.navigation.navigateLogin

internal class MainNavigator(
    val navController: NavHostController,
) {
    val startDestination = Route.Login

    fun navigateLogin() {
        navController.navigateLogin()
    }

    fun navigateDevice() {
        // TODO
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
