package com.pachuho.benchmark.feature.login.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pachuho.benchmark.core.navigation.Route
import com.pachuho.benchmark.feature.login.LoginRoute

fun NavController.navigateLogin() {
    navigate(Route.Login)
}

fun NavGraphBuilder.loginNavGraph(
    padding: PaddingValues,
    onLoginSuccess: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    composable<Route.Login> {
        LoginRoute(padding, onLoginSuccess, onShowErrorSnackBar)
    }
}
