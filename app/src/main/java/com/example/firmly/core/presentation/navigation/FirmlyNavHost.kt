package com.example.firmly.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.firmly.AppBarState
import com.example.firmly.FirmlyAppState
import com.example.firmly.contractors.navigation.contractorsScreen
import com.example.firmly.home.navigation.HOME_ROUTE
import com.example.firmly.home.navigation.homeScreen
import com.example.firmly.search.navigation.searchScreen
import com.example.firmly.settings.navigation.settingsScreen

@Composable
fun FirmlyNavHost(
    appState: FirmlyAppState,
    modifier: Modifier = Modifier,
    startDestination: String = HOME_ROUTE,
    onComposing: (AppBarState) -> Unit
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen(onBackClick = { navController.popBackStack() }, onComposing = onComposing)
        contractorsScreen(onBackClick = { navController.popBackStack() }, onComposing = onComposing)
        searchScreen(onBackClick = { navController.popBackStack() }, onComposing = onComposing)
        settingsScreen(onBackClick = { navController.popBackStack() }, onComposing = onComposing)
    }
}