package com.example.firmly.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.firmly.AppBarState
import com.example.firmly.core.presentation.navigation.PlaceholderScreen

@Composable
internal fun HomeRoute(
    onBackClick: () -> Unit,
    onComposing: (AppBarState) -> Unit
) {

    var test by remember { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                title = "Home",
                actions = {
                    IconButton(onClick = { test = "Morevert" }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null
                        )
                    }
                }
            )
        )
    }
    PlaceholderScreen(test)
}
