package com.pachuho.benchmark.feature.login.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pachuho.benchmark.core.navigation.Route
import com.pachuho.benchmark.feature.login.LoginRoute

fun NavController.navigateLogin(navOptions: NavOptions) {
    navigate(Route.Login, navOptions)
}

fun NavGraphBuilder.loginNavGraph(
    padding: PaddingValues,
    onLoginSuccess: () -> Unit,
    onShowErrorSnackBar: (message: Int) -> Unit,
) {
    composable<Route.Login> {
        LoginRoute(padding, onLoginSuccess, onShowErrorSnackBar)
    }
}
