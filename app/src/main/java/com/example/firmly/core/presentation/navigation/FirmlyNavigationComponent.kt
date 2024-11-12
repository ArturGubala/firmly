package com.example.firmly.core.presentation.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldLayout
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute


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
        content()
    }
}

@Composable
fun FirmlyBottomNavigationBar(
    currentDestination: NavDestination?,
    navigateToTopLevelDestination: (FirmlyTopLevelDestination) -> Unit
) {
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        TOP_LEVEL_DESTINATIONS.forEach { replyDestination ->
            NavigationBarItem(
                selected = currentDestination.hasRoute(replyDestination),
                onClick = { navigateToTopLevelDestination(replyDestination) },
                icon = {
                    Icon(
                        imageVector = replyDestination.selectedIcon,
                        contentDescription = stringResource(id = replyDestination.iconTextId)
                    )
                }
            )
        }
    }
}

fun NavDestination?.hasRoute(destination: FirmlyTopLevelDestination): Boolean =
    this?.hasRoute(destination.route::class) ?: false
