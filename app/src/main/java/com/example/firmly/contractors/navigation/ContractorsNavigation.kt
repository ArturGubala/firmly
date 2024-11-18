package com.example.firmly.contractors.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.firmly.AppBarState
import com.example.firmly.contractors.ContractorsRoute

const val CONTRACTORS_ROUTE = "contractors_route"

fun NavController.navigateToContractors(navOptions: NavOptions? = null) = navigate(CONTRACTORS_ROUTE, navOptions)

fun NavGraphBuilder.contractorsScreen(
    onBackClick: () -> Unit,
    onComposing: (AppBarState) -> Unit
) {
    composable(route = CONTRACTORS_ROUTE) {
        ContractorsRoute(
            onBackClick = onBackClick,
            onComposing = onComposing
        )
    }
}
