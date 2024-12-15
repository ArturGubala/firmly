package com.example.firmly.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.firmly.core.presentation.components.ContractorCard
import com.example.firmly.core.presentation.components.FirmlyTopAppBar
import com.example.firmly.core.presentation.navigation.TopLevelDestination
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeRoute(
    onBackClick: () -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        onBackClick = onBackClick,
        state = state,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    onBackClick: () -> Unit,
    state: HomeState,
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

            Column(
                modifier = Modifier
                    .padding(padding)
            ) {
                LazyRow(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                ) {
                    itemsIndexed(state.savedContractors) { index, contractor ->
                        ContractorCard(
                            contractor = contractor,
                            modifier = Modifier
                                .width(300.dp)
                                .padding(top = 5.dp, start = 10.dp),
                        )
                    }
                }

                LazyRow(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                ) {
                    itemsIndexed(state.temporaryContractors) { index, contractor ->
                        ContractorCard(
                            contractor = contractor,
                            modifier = Modifier
                                .width(300.dp)
                                .padding(top = 5.dp, start = 10.dp),
                        )
                    }
                }
            }
        }
    )
}
