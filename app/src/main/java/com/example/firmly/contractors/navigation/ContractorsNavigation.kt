package com.example.firmly.contractors.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.firmly.contractors.presentation.contractor_detail.ContractorDetailRoute
import com.example.firmly.contractors.presentation.contractor_list.ContractorsListRoute

const val CONTRACTORS_LIST_ROUTE = "contractors_route"
const val CONTRACTOR_DETAIL_ROUTE = "$CONTRACTORS_LIST_ROUTE/{contractorId}"

fun NavController.navigateToContractors(navOptions: NavOptions? = null) = navigate(CONTRACTORS_LIST_ROUTE, navOptions)

fun NavController.navigateToContractorDetail(contractorId: String, navOptions: NavOptions? = null) {
    navigate(CONTRACTOR_DETAIL_ROUTE.replace("{contractorId}", contractorId), navOptions)
}

fun NavGraphBuilder.contractorListScreen(
    navController: NavController,
    onBackClick: () -> Unit,
) {
    composable(route = CONTRACTORS_LIST_ROUTE) {
        ContractorsListRoute(
            navController = navController,
            onBackClick = onBackClick,
        )
    }
}

fun NavGraphBuilder.contractorDetailScreen(
    onBackClick: () -> Unit,
) {
    composable(
        route = CONTRACTOR_DETAIL_ROUTE,
        arguments = listOf(navArgument("contractorId") { type = NavType.StringType })
    ) { backStackEntry ->
        val contractorId = backStackEntry.arguments?.getString("contractorId") ?: ""
        ContractorDetailRoute(
            contractorId = contractorId,
            onBackClick = onBackClick
        )
    }
}
