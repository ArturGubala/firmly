package com.example.firmly.search

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.HideImage
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.firmly.AppBarState
import com.example.firmly.core.presentation.navigation.PlaceholderScreen

@Composable
internal fun SearchRoute(
    onBackClick: () -> Unit,
    onComposing: (AppBarState) -> Unit
) {

    var test by remember { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                title = "Search",
                actions = {
                    IconButton(onClick = { test = "Filter" }) {
                        Icon(
                            imageVector = Icons.Default.FilterAlt,
                            contentDescription = null
                        )
                    }
                }
            )
        )
    }

    PlaceholderScreen(test)
}
