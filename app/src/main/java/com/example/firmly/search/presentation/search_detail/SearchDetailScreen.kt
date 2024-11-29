package com.example.firmly.search.presentation.search_detail

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.firmly.core.presentation.components.DetailScreenContent
import com.example.firmly.core.presentation.components.FirmlyTopAppBar
import com.example.firmly.core.presentation.util.ObserveAsEvents
import com.example.firmly.search.presentation.search_list.SearchListEvent
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun SearchDetailRoute(
    contractorId: String,
    onBackClick: () -> Unit,
    viewModel: SearchDetailViewModel = koinViewModel(
        parameters = { parametersOf(contractorId) }
    )
) {

    val context = LocalContext.current
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is SearchListEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    SearchDetailScreen(
        onBackClick = onBackClick,
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchDetailScreen(
    onBackClick: () -> Unit,
    state: SearchDetailState,
    onAction: (SearchDetailAction) -> Unit
) {
    Scaffold(
        topBar = {
            FirmlyTopAppBar(
                titleRes = null,
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onBackClick,
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add icon",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            )
        }
    ) { padding ->
            if (state.contractor != null){
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
