package com.example.firmly.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.firmly.AppBarState
import com.example.firmly.search.SearchRoute

const val SEARCH_ROUTE = "search_route"

fun NavController.navigateToSearch(navOptions: NavOptions? = null) = navigate(SEARCH_ROUTE, navOptions)

fun NavGraphBuilder.searchScreen(
    onBackClick: () -> Unit,
    onComposing: (AppBarState) -> Unit
) {
    composable(route = SEARCH_ROUTE) {
        SearchRoute(
            onBackClick = onBackClick,
            onComposing = onComposing
        )
    }
}
