package com.example.firmly.search.presentation.search_list

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.firmly.core.presentation.components.FirmlyTopAppBar
import com.example.firmly.core.presentation.navigation.TopLevelDestination
import com.example.firmly.core.presentation.util.ObserveAsEvents
import com.example.firmly.search.navigation.navigateToSearchDetail
import com.example.firmly.core.presentation.components.TextWithTitle
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SearchListRoute(
    navController: NavController,
    onBackClick: () -> Unit,
    viewModel: SearchListViewModel = koinViewModel()
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
            is SearchListEvent.NavigateToDetail -> {
                navController.navigateToSearchDetail(event.contractorId)
            }
        }
    }

    SearchListScreen(
        onBackClick = onBackClick,
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchListScreen(
    onBackClick: () -> Unit,
    state: SearchListState,
    onAction: (SearchListAction) -> Unit
) {

    var openBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()
    var showText by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            FirmlyTopAppBar(
                titleRes = TopLevelDestination.SEARCH.titleTextId,
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onBackClick,
                actions = {
                    IconButton(onClick = { openBottomSheet = !openBottomSheet }) {
                        Icon(
                            imageVector = Icons.Default.FilterAlt,
                            contentDescription = "Filter icon",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            )
        },
        content = { padding ->

            Column(
                modifier = Modifier.Companion
                    .padding(padding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Companion.CenterHorizontally
            ) {
                LazyColumn(
                    modifier = Modifier.Companion.fillMaxSize(),
                ) {
                    items(state.contractors) { contractor ->
                        Card(
                            modifier = Modifier.Companion
                                .fillMaxWidth(.96f)
                                .padding(top = 10.dp),
                            onClick = { onAction(SearchListAction.OnContractorCardClick(contractor.id)) }
                        ) {
                            Column(
                                modifier = Modifier.Companion
                                    .padding(horizontal = 7.dp, vertical = 5.dp)
                            ) {
                                TextWithTitle(
                                    fieldName = "Nazwa",
                                    fieldText = contractor.name,
                                    bottomSpace = 10.dp
                                )
                                Row(
                                    Modifier.Companion
                                        .fillMaxWidth()
                                ) {
                                    TextWithTitle(
                                        fieldName = "Kod pocztowy",
                                        fieldText = contractor.postalCode ?: "",
                                        occupyWidth = .4f
                                    )
                                    TextWithTitle(
                                        fieldName = "Miasto",
                                        fieldText = contractor.city ?: ""
                                    )
                                }
                                Row(
                                    Modifier.Companion
                                        .fillMaxWidth()
                                ) {
                                    TextWithTitle(
                                        fieldName = "NIP",
                                        fieldText = contractor.taxNumber,
                                        bottomSpace = 0.dp,
                                        occupyWidth = .4f
                                    )
                                    TextWithTitle(
                                        fieldName = "Regon",
                                        fieldText = contractor.buisnessRegistryNumber,
                                        bottomSpace = 0.dp,
                                    )
                                }
                            }
                        }
                    }
                }

                if (openBottomSheet) {

                    ModalBottomSheet(
                        onDismissRequest = { openBottomSheet = false },
                        sheetState = bottomSheetState,
                    ) {
                        Column(modifier = Modifier.Companion.padding(horizontal = 10.dp)) {
                            Row(
                                Modifier.Companion
                                    .fillMaxWidth(),
                            ) {
                                OutlinedTextField(
                                    value = state.queryParameters.taxNumber,
                                    onValueChange = { onAction(SearchListAction.OnTaxNumberFieldEnter(it)) },
                                    Modifier.Companion
                                        .fillMaxWidth(.5f)
                                        .padding(end = 5.dp),
                                    label = { Text("NIP") },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Companion.Number,
                                        imeAction = ImeAction.Companion.Next
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onSearch = {
                                            showText = true
                                        }
                                    ),
                                    trailingIcon = {
                                        if (state.queryParameters.taxNumber.isNotBlank()) {
                                            Icon(Icons.Default.Clear,
                                                contentDescription = "clear text",
                                                modifier = Modifier.Companion.clickable {
                                                    onAction(
                                                        SearchListAction.OnClearFieldIconClick(
                                                            SearchListViewModel.Field.TAX_NUMBER
                                                        )
                                                    )
                                                }
                                            )
                                        }
                                    }
                                )

                                OutlinedTextField(
                                    value = state.queryParameters.businessRegistryNumber,
                                    onValueChange = {
                                        onAction(
                                            SearchListAction.OnBusinessRegistryNumberFieldEnter(
                                                it
                                            )
                                        )
                                    },
                                    label = { Text("REGON") },
                                    modifier = Modifier.Companion
                                        .fillMaxWidth()
                                        .padding(start = 5.dp),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Companion.Number,
                                        imeAction = ImeAction.Companion.Next
                                    ),
                                    trailingIcon = {
                                        if (state.queryParameters.businessRegistryNumber.isNotBlank()) {
                                            Icon(Icons.Default.Clear,
                                                contentDescription = "clear text",
                                                modifier = Modifier.Companion.clickable {
                                                    onAction(
                                                        SearchListAction.OnClearFieldIconClick(
                                                            SearchListViewModel.Field.BUSINESS_REGISTRY_NUMBER
                                                        )
                                                    )
                                                }
                                            )
                                        }
                                    }
                                )
                            }

                            Row(
                                Modifier.Companion
                                    .fillMaxWidth()
                                    .padding(bottom = 5.dp),
                            ) {
                                OutlinedTextField(
                                    value = state.queryParameters.name,
                                    onValueChange = { onAction(SearchListAction.OnNameFieldEnter(it)) },
                                    label = { Text("NAZWA") },
                                    modifier = Modifier.Companion.fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions(
                                        imeAction = ImeAction.Companion.Next
                                    ),
                                    trailingIcon = {
                                        if (state.queryParameters.name.isNotBlank()) {
                                            Icon(Icons.Default.Clear,
                                                contentDescription = "clear text",
                                                modifier = Modifier.Companion.clickable {
                                                    onAction(
                                                        SearchListAction.OnClearFieldIconClick(
                                                            SearchListViewModel.Field.NAME
                                                        )
                                                    )
                                                }
                                            )
                                        }
                                    }
                                )
                            }

                            Row(
                                Modifier.Companion
                                    .fillMaxWidth()
                                    .padding(bottom = 5.dp),
                            ) {
                                OutlinedTextField(
                                    value = state.queryParameters.firstName,
                                    onValueChange = { onAction(SearchListAction.OnFirstNameFieldEnter(it)) },
                                    Modifier.Companion
                                        .fillMaxWidth(.5f)
                                        .padding(end = 5.dp),
                                    label = { Text("IMIE") },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Companion.Text,
                                        imeAction = ImeAction.Companion.Next
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onSearch = {
                                            showText = true
                                        }
                                    ),
                                    trailingIcon = {
                                        if (state.queryParameters.firstName.isNotBlank()) {
                                            Icon(Icons.Default.Clear,
                                                contentDescription = "clear text",
                                                modifier = Modifier.Companion.clickable {
                                                    onAction(
                                                        SearchListAction.OnClearFieldIconClick(
                                                            SearchListViewModel.Field.FIRST_NAME
                                                        )
                                                    )
                                                }
                                            )
                                        }
                                    }
                                )

                                OutlinedTextField(
                                    value = state.queryParameters.lastName,
                                    onValueChange = { onAction(SearchListAction.OnLastNameFieldEnter(it)) },
                                    label = { Text("NAZWISKO") },
                                    modifier = Modifier.Companion
                                        .fillMaxWidth()
                                        .padding(start = 5.dp),
                                    keyboardOptions = KeyboardOptions(
                                        imeAction = ImeAction.Companion.Next
                                    ),
                                    trailingIcon = {
                                        if (state.queryParameters.lastName.isNotBlank()) {
                                            Icon(Icons.Default.Clear,
                                                contentDescription = "clear text",
                                                modifier = Modifier.Companion.clickable {
                                                    onAction(
                                                        SearchListAction.OnClearFieldIconClick(
                                                            SearchListViewModel.Field.LAST_NAME
                                                        )
                                                    )
                                                }
                                            )
                                        }
                                    }
                                )
                            }

                            Row(
                                Modifier.Companion
                                    .fillMaxWidth()
                                    .padding(bottom = 5.dp),
                            ) {
                                FilledIconButton(
                                    onClick = {
                                        openBottomSheet = false
                                        onAction(SearchListAction.OnSearchContractorClick)
                                    },
                                    Modifier.Companion
                                        .fillMaxWidth()
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.Companion.CenterVertically
                                    ) {
                                        Icon(
                                            Icons.Default.Search,
                                            contentDescription = "search",
                                        )
                                        Text(text = "WYSZUKAJ")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}