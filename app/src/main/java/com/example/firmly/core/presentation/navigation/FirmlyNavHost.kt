package com.example.firmly.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.firmly.FirmlyAppState
import com.example.firmly.contractors.navigation.contractorDetailScreen
import com.example.firmly.contractors.navigation.contractorListScreen
import com.example.firmly.home.navigation.HOME_ROUTE
import com.example.firmly.home.navigation.homeScreen
import com.example.firmly.search.navigation.searchDetailScreen
import com.example.firmly.search.navigation.searchListScreen
import com.example.firmly.settings.navigation.settingsScreen

@Composable
fun FirmlyNavHost(
    appState: FirmlyAppState,
    modifier: Modifier = Modifier,
    startDestination: String = HOME_ROUTE,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen(onBackClick = { navController.popBackStack() })
        contractorListScreen(
            navController = navController,
            onBackClick = { navController.popBackStack() }
        )
        contractorDetailScreen( onBackClick = { navController.popBackStack() } )
        searchListScreen(
            navController = navController,
            onBackClick = { navController.popBackStack() }
        )
        searchDetailScreen { navController.popBackStack() }
        settingsScreen(onBackClick = { navController.popBackStack() })
    }
}
