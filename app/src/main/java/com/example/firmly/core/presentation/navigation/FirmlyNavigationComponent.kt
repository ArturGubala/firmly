package com.example.firmly.core.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.material3.Text
import androidx.compose.ui.platform.testTag


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
                CenterAlignedTopAppBar(
                    title = { Text(text = currentDestination?.route?.split(".")?.last() ?: "Top App Bar") },
                    navigationIcon = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {  }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    },
                    modifier = Modifier.testTag("firmlyTopAppBar"),
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
