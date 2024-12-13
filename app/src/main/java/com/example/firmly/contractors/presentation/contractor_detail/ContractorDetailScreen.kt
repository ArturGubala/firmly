package com.example.firmly.contractors.presentation.contractor_detail

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.firmly.core.presentation.components.DetailScreenContent
import com.example.firmly.core.presentation.components.FirmlyTopAppBar
import com.example.firmly.core.presentation.util.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun ContractorDetailRoute (
    contractorId: String,
    onBackClick: () -> Unit,
    viewModel: ContractorDetailViewModel = koinViewModel(
        parameters = { parametersOf(contractorId) }
    )
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ContractorDetailEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error,
                    Toast.LENGTH_LONG
                ).show()
            }
            is ContractorDetailEvent.Success -> {
                Toast.makeText(
                    context,
                    event.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    ContractorDetailScreen(
        onBackClick = onBackClick,
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContractorDetailScreen(
    onBackClick: () -> Unit,
    state: ContractorDetailState,
    onAction: (ContractorDetailAction) -> Unit
) {
    Scaffold(
        topBar = {
            FirmlyTopAppBar(
                titleRes = null,
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = { onBackClick() },
                actions = {
                    if (state.contractor != null && state.contractor.temporary) {
                        IconButton(onClick = { onAction(ContractorDetailAction.OnAddContractorClick) }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add icon",
                                tint = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    } else {
                        IconButton(onClick = {  }) {
                            Icon(
                                imageVector = Icons.Default.Update,
                                contentDescription = "Update icon",
                                tint = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                        IconButton(onClick = {
                            onAction(ContractorDetailAction.OnDeleteContractorClick(state.contractor!!.id))
                            onBackClick()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete icon",
                                tint = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (state.contractor != null){
            DetailScreenContent(
                contractorDetail = state.contractor,
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth(),
            )
        }
    }
}