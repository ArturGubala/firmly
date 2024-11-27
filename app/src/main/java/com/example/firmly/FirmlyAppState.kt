package com.example.firmly

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.tracing.trace
import com.example.firmly.contractors.navigation.navigateToContractors
import com.example.firmly.core.presentation.navigation.TopLevelDestination
import com.example.firmly.core.presentation.navigation.TopLevelDestination.CONTRACTORS
import com.example.firmly.core.presentation.navigation.TopLevelDestination.HOME
import com.example.firmly.core.presentation.navigation.TopLevelDestination.SEARCH
import com.example.firmly.core.presentation.navigation.TopLevelDestination.SETTINGS
import com.example.firmly.home.navigation.navigateToHome
import com.example.firmly.search.navigation.navigateToSearchList
import com.example.firmly.settings.navigation.navigateToSettings

@Composable
fun rememberFirmlyAppState(
    navController: NavHostController = rememberNavController(),
): FirmlyAppState {
    return remember(navController) {
        FirmlyAppState(navController)
    }
}

@Stable
class FirmlyAppState(
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }

            when (topLevelDestination) {
                HOME -> navController.navigateToHome(topLevelNavOptions)
                CONTRACTORS -> navController.navigateToContractors(topLevelNavOptions)
                SEARCH -> navController.navigateToSearchList(topLevelNavOptions)
                SETTINGS -> navController.navigateToSettings(topLevelNavOptions)
            }
        }
    }
}
