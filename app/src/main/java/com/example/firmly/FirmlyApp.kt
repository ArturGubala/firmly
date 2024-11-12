package com.example.firmly

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.firmly.core.presentation.navigation.FirmlyNavigationActions
import com.example.firmly.core.presentation.navigation.PlaceholderScreen
import com.example.firmly.core.presentation.navigation.Route
import com.example.firmly.core.presentation.navigation.FirmlyNavigationWrapper

@Composable
fun FirmlyApp() {

    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        FirmlyNavigationActions(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Surface {
        FirmlyNavigationWrapper(
            currentDestination = currentDestination,
            navigateToTopLevelDestination = navigationActions::navigateTo
        ) {
            FirmlyNavHost(
                navController = navController
            )
        }
    }
}

@Composable
private fun FirmlyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Route.Home,
    ) {
        composable<Route.Home> {
            PlaceholderScreen("HOME")
        }
        composable<Route.Contractors> {
            PlaceholderScreen("CONTRACTORS")
        }
        composable<Route.Search> {
            PlaceholderScreen("SEARCH")
        }
        composable<Route.Settings> {
            PlaceholderScreen("SETTINGS")
        }
    }
}
