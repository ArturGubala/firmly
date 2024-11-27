package com.example.firmly.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.firmly.search.presentation.search_detail.SearchDetailRoute
import com.example.firmly.search.presentation.search_list.SearchListRoute

const val SEARCH_LIST_ROUTE = "search_route"
const val SEARCH_DETAIL_ROUTE = "$SEARCH_LIST_ROUTE/{contractorId}"

fun NavController.navigateToSearchList(navOptions: NavOptions? = null) = navigate(SEARCH_LIST_ROUTE, navOptions)

fun NavController.navigateToSearchDetail(contractorId: String, navOptions: NavOptions? = null) {
    navigate(SEARCH_DETAIL_ROUTE.replace("{contractorId}", contractorId), navOptions)
}

fun NavGraphBuilder.searchListScreen(
    navController: NavController,
    onBackClick: () -> Unit,
) {
    composable(route = SEARCH_LIST_ROUTE) {
        SearchListRoute(
            navController = navController,
            onBackClick = onBackClick,
        )
    }
}

fun NavGraphBuilder.searchDetailScreen(
    onBackClick: () -> Unit,
) {
    composable(
        route = SEARCH_DETAIL_ROUTE,
        arguments = listOf(navArgument("contractorId") { type = NavType.StringType })
    ) { backStackEntry ->
        val contractorId = backStackEntry.arguments?.getString("contractorId") ?: ""
        SearchDetailRoute(
            contractorId = contractorId,
            onBackClick = onBackClick
        )
    }
}
