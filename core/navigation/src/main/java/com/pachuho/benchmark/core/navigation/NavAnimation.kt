package com.pachuho.benchmark.core.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pachuho.benchmark.core.navigation.NavAnimation.enterTransition
import com.pachuho.benchmark.core.navigation.NavAnimation.exitTransition
import com.pachuho.benchmark.core.navigation.NavAnimation.popEnterTransition
import com.pachuho.benchmark.core.navigation.NavAnimation.popExitTransition

object NavAnimation {
    val enterTransition: @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
            animationSpec = tween(DURATION_MILLS)
        )
    }

    val exitTransition: @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
            animationSpec = tween(DURATION_MILLS)
        )
    }

    val popEnterTransition: @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
            animationSpec = tween(DURATION_MILLS)
        )
    }

    val popExitTransition: @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
            animationSpec = tween(DURATION_MILLS)
        )
    }

    private const val DURATION_MILLS = 500
}

inline fun <reified R : Route> NavGraphBuilder.composableWithAnimation(
    arguments: List<NamedNavArgument> = emptyList(),
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    val routeString = R::class
        .java
        .getDeclaredField("ROUTE")
        .get(null) as String

    composable(
        route = routeString,
        arguments = arguments,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        content = content
    )
}
