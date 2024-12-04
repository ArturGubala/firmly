package com.example.firmly.contractors.presentation.contractor_list

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.firmly.core.presentation.components.FirmlyTopAppBar
import com.example.firmly.core.presentation.navigation.TopLevelDestination
import com.example.firmly.core.presentation.util.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ContractorsListRoute(
    navController: NavController,
    onBackClick: () -> Unit,
    viewModel: ContractorListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ContractorListEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error,
                    Toast.LENGTH_LONG
                ).show()
            }
            is ContractorListEvent.NavigateToDetail -> {
//                navController.navigateToSearchDetail(event.contractorId)
            }
        }
    }

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

            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(state.contractors) { contractor ->
                        ListItem(
                            modifier = Modifier.clickable {
                                onAction(ContractorListAction.OnContractorCardClick(contractor.id))
                            },
                            headlineContent = {
                                Text(text = contractor.name)
                            },
                            supportingContent = {
                                Row {
                                    Text(text = "NIP: ${contractor.taxNumber}")
                                    Text(text = " | ")
                                    Text(text = "REGON: ${contractor.buisnessRegistryNumber}")
                                }
                            }
                        )
                    }
                }
            }
        }
    )
}
