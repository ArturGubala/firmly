package com.example.firmly.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.firmly.core.presentation.components.FirmlyTopAppBar
import com.example.firmly.core.presentation.navigation.PlaceholderScreen
import com.example.firmly.core.presentation.navigation.TopLevelDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeRoute(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            FirmlyTopAppBar(
                titleRes = TopLevelDestination.HOME.titleTextId,
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onBackClick,
            )
        },
        content = { padding ->
            PlaceholderScreen(
                routeName = "HOME",
                modifier = Modifier.padding(padding)
            )
        }
    )
}
