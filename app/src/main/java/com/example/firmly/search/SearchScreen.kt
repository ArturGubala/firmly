package com.example.firmly.search

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.firmly.core.presentation.components.FirmlyTopAppBar
import com.example.firmly.core.presentation.navigation.PlaceholderScreen
import com.example.firmly.core.presentation.navigation.TopLevelDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchRoute(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            FirmlyTopAppBar(
                titleRes = TopLevelDestination.SEARCH.titleTextId,
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onBackClick,
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.FilterAlt,
                            contentDescription = "Filter icon",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            )
        },
        content = { padding ->
            PlaceholderScreen(
                routeName = "SEARCH",
                modifier = Modifier.padding(padding)
            )
        }
    )
}
