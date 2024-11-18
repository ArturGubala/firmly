package com.example.firmly.contractors

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
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
internal fun ContractorsRoute(
    onBackClick: () -> Unit,
    onComposing: (AppBarState) -> Unit
) {
    var test by remember { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                title = "Contractors",
                actions = {
                    IconButton(onClick = { test = "Contractors" }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    }
                }
            )
        )
    }

    PlaceholderScreen(test)
}
