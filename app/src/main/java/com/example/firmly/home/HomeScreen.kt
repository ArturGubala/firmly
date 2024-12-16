package com.example.firmly.home

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (state.savedContractors.isNotEmpty() || state.temporaryContractors.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(padding)
                ) {
                    Text(
                        text = "Ostatnio zapisani".uppercase(),
                        modifier = Modifier.padding(start = 10.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .padding(top = 5.dp, bottom = 10.dp, start = 10.dp)
                            .background(MaterialTheme.colorScheme.onPrimary)
                            .fillMaxWidth(.3f),
                        thickness = 1.dp
                    )
                    Row(

                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
//                        .padding(horizontal = 5.dp),
                    ) {
                        for (contractor in state.savedContractors) {
                            ContractorCard(
                                contractor = contractor,
                                modifier = Modifier
                                    .width(300.dp)
                                    .padding(top = 5.dp, start = 10.dp)
                                    .requiredHeight(220.dp),
                            )
                        }
                    }

                    Text(
                        text = "Ostatnio przeglądani".uppercase(),
                        modifier = Modifier.padding(start = 10.dp, top = 20.dp),
                        style = MaterialTheme.typography.titleLarge,
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .padding(top = 5.dp, bottom = 10.dp, start = 10.dp)
                            .background(MaterialTheme.colorScheme.onPrimary)
                            .fillMaxWidth(.3f),
                        thickness = 1.dp
                    )
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 5.dp),
                    ) {
                        for (contractor in state.temporaryContractors) {
                            ContractorCard(
                                contractor = contractor,
                                modifier = Modifier
                                    .width(300.dp)
                                    .padding(top = 5.dp, start = 10.dp)
                                    .height(220.dp),
                            )
                        }
                    }
                }
            }
        }
    )
}
