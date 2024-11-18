package com.example.firmly.core.presentation.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.firmly.AppBarState
import com.example.firmly.ui.theme.FirmlyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirmlyTopAppBar(
    @StringRes titleRes: Int,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String,
    actionIcon: ImageVector,
    actionIconContentDescription: String,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
    appBarState: AppBarState
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(id = titleRes)) },

        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
//        actions = {
//            IconButton(onClick = onActionClick) {
//                Icon(
//                    imageVector = actionIcon,
//                    contentDescription = actionIconContentDescription,
//                    tint = MaterialTheme.colorScheme.onSurface,
//                )
//            }
//        },
        colors = colors,
        modifier = modifier.testTag("niaTopAppBar"),
        actions = {
            appBarState.actions?.invoke(this)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Top App Bar")
@Composable
private fun FirmlyTopAppBarPreview() {
    FirmlyTheme {
        FirmlyTopAppBar(
            titleRes = android.R.string.untitled,
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            navigationIconContentDescription = "Navigation icon",
            actionIcon = Icons.Default.Search,
            actionIconContentDescription = "Action icon",
            appBarState = AppBarState()
        )
    }
}
