package com.example.firmly.contractors.presentation.contractor_list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.firmly.core.presentation.components.FirmlyTopAppBar
import com.example.firmly.core.presentation.navigation.TopLevelDestination
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ContractorsListRoute(
    navController: NavController,
    onBackClick: () -> Unit,
    viewModel: ContractorListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ContractorListScreen(
        onBackClick = onBackClick,
        state = state,
        onAction = viewModel::onAction
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContractorListScreen(
    onBackClick: () -> Unit,
    state: ContractorListState,
    onAction: (ContractorListAction) -> Unit
) {
    Scaffold(
        topBar = {
            FirmlyTopAppBar(
                titleRes = TopLevelDestination.CONTRACTORS.titleTextId,
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onBackClick,
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Filter icon",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            )
        },
        content = { padding ->

            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(state.contractors) { contractor ->
                    Text(text = contractor.name)
                }
            }
        }
    )
}
