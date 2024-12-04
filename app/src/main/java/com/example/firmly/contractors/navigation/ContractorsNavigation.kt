package com.example.firmly.contractors.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.firmly.contractors.presentation.contractor_list.ContractorsListRoute

const val CONTRACTORS_ROUTE = "contractors_route"

fun NavController.navigateToContractors(navOptions: NavOptions? = null) = navigate(CONTRACTORS_ROUTE, navOptions)

fun NavGraphBuilder.contractorsScreen(
    navController: NavController,
    onBackClick: () -> Unit,
) {
    composable(route = CONTRACTORS_ROUTE) {
        ContractorsListRoute(
            navController = navController,
            onBackClick = onBackClick,
        )
    }
}
