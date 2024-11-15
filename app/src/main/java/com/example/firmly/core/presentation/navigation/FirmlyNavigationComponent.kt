package com.example.firmly.core.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldLayout
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import com.example.firmly.core.presentation.components.FirmlyTopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirmlyNavigationWrapper(
    currentDestination: NavDestination?,
    navigateToTopLevelDestination: (FirmlyTopLevelDestination) -> Unit,
    content: @Composable () -> Unit
) {

    NavigationSuiteScaffoldLayout(
        layoutType = NavigationSuiteType.NavigationBar,
        navigationSuite = {
            FirmlyBottomNavigationBar(
                currentDestination = currentDestination,
                navigateToTopLevelDestination = navigateToTopLevelDestination
            )
        }
    ) {
        Scaffold(
            modifier = Modifier,
            topBar = {
                FirmlyTopAppBar(
                    titleRes = getTopLevelDestination(currentDestination).iconTextId,
                    navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                    navigationIconContentDescription = "Navigation icon",
                    actionIcon = Icons.Default.Search,
                    actionIconContentDescription = "Action icon",
                )
            }
        ) { values ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(values)
            ) {
                content()
            }
        }
    }
}

@Composable
fun FirmlyBottomNavigationBar(
    currentDestination: NavDestination?,
    navigateToTopLevelDestination: (FirmlyTopLevelDestination) -> Unit
) {
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        TOP_LEVEL_DESTINATIONS.forEach { firmlyDestination ->
            NavigationBarItem(
                selected = currentDestination.hasRoute(firmlyDestination),
                onClick = { navigateToTopLevelDestination(firmlyDestination) },
                icon = {
                    Icon(
                        imageVector = firmlyDestination.selectedIcon,
                        contentDescription = stringResource(id = firmlyDestination.iconTextId)
                    )
                }
            )
        }
    }
}

fun NavDestination?.hasRoute(destination: FirmlyTopLevelDestination): Boolean =
    this?.hasRoute(destination.route::class) ?: false

fun getTopLevelDestination(destination: NavDestination?): FirmlyTopLevelDestination {
    return when (destination?.route?.split(".")?.last()?.uppercase()) {
        "HOME" -> TOP_LEVEL_DESTINATIONS.first { it.route is Route.Home }
        "CONTRACTORS" -> TOP_LEVEL_DESTINATIONS.first { it.route is Route.Contractors }
        "SEARCH" -> TOP_LEVEL_DESTINATIONS.first { it.route is Route.Search }
        "SETTINGS" -> TOP_LEVEL_DESTINATIONS.first { it.route is Route.Settings }
        else -> TOP_LEVEL_DESTINATIONS.first { it.route is Route.Home }
    }
}
