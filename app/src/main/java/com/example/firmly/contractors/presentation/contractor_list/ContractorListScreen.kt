package com.example.firmly.contractors.presentation.contractor_list

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.firmly.contractors.navigation.navigateToContractorDetail
import com.example.firmly.core.presentation.components.FirmlyTopAppBar
import com.example.firmly.core.presentation.navigation.TopLevelDestination
import com.example.firmly.core.presentation.util.ObserveAsEvents
import kotlinx.coroutines.launch
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
                navController.navigateToContractorDetail(event.contractorId)
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
                actions = { }
            )
        },
        content = { padding ->
            val pagerState = rememberPagerState(pageCount = { 2 })

            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                Column(
                    modifier = Modifier.padding(padding)
                ) {
                    SecondaryTabRow(
                        selectedTabIndex = pagerState.currentPage
                    ) {
                        val scope = rememberCoroutineScope()

                        Tab(
                            selected = true,
                            onClick = {
                                scope.launch {
                                    pagerState.scrollToPage(0)
                                }
                            },
                            text = { Text("Zapisani") }

                        )
                        Tab(
                            selected = true,
                            onClick = {
                                scope.launch {
                                    pagerState.scrollToPage(1)
                                }
                            },
                            text = { Text("Przeglądani") }

                        )
                    }
                    HorizontalPager(
                        state = pagerState
                    ) { page ->
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            itemsIndexed(state.contractors.filter { it.temporary == (page == 1) }) { index, contractor ->
                                ListItem(
                                    modifier = Modifier.clickable {
                                        onAction(ContractorListAction.OnContractorClick(contractor.id))
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
                                if (index != state.contractors.lastIndex){
                                    HorizontalDivider()
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}
