package com.example.firmly.core.presentation.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun FirmlyNavigationSuiteScaffold(
    navigationSuiteItems: FirmlyNavigationSuiteScope.() -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val layoutType = NavigationSuiteType.NavigationBar
    val navigationSuiteItemColors = NavigationSuiteItemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = FirmlyNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = FirmlyNavigationDefaults.navigationContentColor(),
            selectedTextColor = FirmlyNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = FirmlyNavigationDefaults.navigationContentColor(),
            indicatorColor = FirmlyNavigationDefaults.navigationIndicatorColor(),
        ),
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            selectedIconColor = FirmlyNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = FirmlyNavigationDefaults.navigationContentColor(),
            selectedTextColor = FirmlyNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = FirmlyNavigationDefaults.navigationContentColor(),
            indicatorColor = FirmlyNavigationDefaults.navigationIndicatorColor(),
        ),
        navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
            selectedIconColor = FirmlyNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = FirmlyNavigationDefaults.navigationContentColor(),
            selectedTextColor = FirmlyNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = FirmlyNavigationDefaults.navigationContentColor(),
        ),
    )

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            FirmlyNavigationSuiteScope(
                navigationSuiteScope = this,
                navigationSuiteItemColors = navigationSuiteItemColors
            ).run(navigationSuiteItems)
        },
        layoutType = layoutType,
        containerColor = Color.Transparent,
        modifier = modifier,
    ) {
        content()
    }
}

class FirmlyNavigationSuiteScope internal constructor(
    private val navigationSuiteScope: NavigationSuiteScope,
    private val navigationSuiteItemColors: NavigationSuiteItemColors,
) {
    fun item(
        selected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        icon: @Composable () -> Unit,
        selectedIcon: @Composable () -> Unit = icon,
        label: @Composable (() -> Unit)? = null,
    ) = navigationSuiteScope.item(
        selected = selected,
        onClick = onClick,
        icon = {
            if (selected) {
                selectedIcon()
            } else {
                icon()
            }
        },
        label = label,
        colors = navigationSuiteItemColors,
        modifier = modifier,
    )
}

object FirmlyNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}
