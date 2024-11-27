package com.example.firmly.search.presentation.search_detail

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.firmly.core.presentation.util.ObserveAsEvents
import com.example.firmly.search.presentation.search_list.SearchListEvent
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SearchDetailRoute(
    contractorId: String,
    onBackClick: () -> Unit,
    viewModel: SearchDetailViewModel = koinViewModel()
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
        contractorId = contractorId,
        onBackClick = onBackClick,
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun SearchDetailScreen(
    contractorId: String,
    onBackClick: () -> Unit,
    state: SearchDetailState,
    onAction: (SearchDetailAction) -> Unit
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = contractorId)
        }

    }
}
