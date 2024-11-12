package com.example.firmly.core.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.firmly.R

data class FirmlyTopLevelDestination(
    val route: Route,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int
)

val TOP_LEVEL_DESTINATIONS = listOf(
    FirmlyTopLevelDestination(
        route = Route.Home,
        selectedIcon = Icons.Default.Home,
        unselectedIcon = Icons.Default.Home,
        iconTextId = R.string.tab_home
    ),
    FirmlyTopLevelDestination(
        route = Route.Contractors,
        selectedIcon = Icons.Default.People,
        unselectedIcon = Icons.Default.People,
        iconTextId = R.string.tab_contractors
    ),
    FirmlyTopLevelDestination(
        route = Route.Search,
        selectedIcon = Icons.Default.Search,
        unselectedIcon = Icons.Default.Search,
        iconTextId = R.string.tab_search
    ),
    FirmlyTopLevelDestination(
        route = Route.Settings,
        selectedIcon = Icons.Default.Settings,
        unselectedIcon = Icons.Default.Settings,
        iconTextId = R.string.tab_settings
    )

)
