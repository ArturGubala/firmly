package com.example.firmly.search.presentation.search_list

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firmly.core.presentation.components.FirmlyTopAppBar
import com.example.firmly.core.presentation.navigation.TopLevelDestination
import com.example.firmly.core.presentation.util.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SearchRoute(
    onBackClick: () -> Unit,
    viewModel: SearchViewModel = koinViewModel()
) {

    val context = LocalContext.current
    ObserveAsEvents(viewModel.events) { event ->
        when(event) {
            is SearchEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    SearchScreen(
        onBackClick = onBackClick,
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreen(
    onBackClick: () -> Unit,
    state: SearchState,
    onAction: (SearchAction) -> Unit
) {

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    var showText by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            FirmlyTopAppBar(
                titleRes = TopLevelDestination.SEARCH.titleTextId,
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onBackClick,
                actions = {
                    IconButton(onClick = { openBottomSheet = !openBottomSheet })  {
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
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(state.contractors) { contractor ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(.96f)
                                .padding(top = 10.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 7.dp, vertical = 5.dp)
                            ) {
                                TextWithDescription(
                                    fieldName = "Nazwa",
                                    fieldText = contractor.name,
                                    bottomSpace = 10.dp
                                )
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                ) {
                                    TextWithDescription(
                                        fieldName = "Kod pocztowy",
                                        fieldText = contractor.postalCode ?: "",
                                        occupyWidth = .4f
                                    )
                                    TextWithDescription(
                                        fieldName = "Miasto",
                                        fieldText = contractor.city ?: ""
                                    )
                                }
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                ) {
                                    TextWithDescription(
                                        fieldName = "NIP",
                                        fieldText = contractor.taxNumber,
                                        bottomSpace = 0.dp,
                                        occupyWidth = .4f
                                    )
                                    TextWithDescription(
                                        fieldName = "Regon",
                                        fieldText = contractor.buisnessRegistryNumber,
                                        bottomSpace = 0.dp,
                                    )
                                }
                            }
                        }
                    }
                }

//                if (state.contractors.any()) {
//                    for (contractor: ContractorListItem in state.contractors) {
//                        Card(
//                            modifier = Modifier
//                                .fillMaxWidth(.96f)
//                                .padding(top = 10.dp)
//                        ) {
//                            Column(
//                                modifier = Modifier
//                                    .padding(horizontal = 7.dp, vertical = 5.dp)
//                            ) {
//                                TextWithDescription(
//                                    fieldName = "Nazwa",
//                                    fieldText = contractor.name,
//                                    bottomSpace = 10.dp
//                                )
//                                Row(
//                                    Modifier
//                                        .fillMaxWidth()
//                                ) {
//                                    TextWithDescription(
//                                        fieldName = "Kod pocztowy",
//                                        fieldText = contractor.postalCode ?: "",
//                                        occupyWidth = .4f
//                                    )
//                                    TextWithDescription(
//                                        fieldName = "Miasto",
//                                        fieldText = contractor.city ?: ""
//                                    )
//                                }
//                                Row(
//                                    Modifier
//                                        .fillMaxWidth()
//                                ) {
//                                    TextWithDescription(
//                                        fieldName = "NIP",
//                                        fieldText = contractor.taxNumber,
//                                        bottomSpace = 0.dp,
//                                        occupyWidth = .4f
//                                    )
//                                    TextWithDescription(
//                                        fieldName = "Regon",
//                                        fieldText = contractor.buisnessRegistryNumber,
//                                        bottomSpace = 0.dp,
//                                    )
//                                }
//                            }
//                        }
//                    }
//                }

                if (openBottomSheet) {

                    ModalBottomSheet(
                        onDismissRequest = { openBottomSheet = false },
                        sheetState = bottomSheetState,
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp, bottom = bottomPadding + 10.dp)
                        ) {
                            Row(
                                Modifier
                                    .padding(bottom = 5.dp)
                                    .fillMaxWidth(),
                            ) {
                                OutlinedTextField(
                                    value = state.queryParameters.taxNumber,
                                    onValueChange = { onAction(SearchAction.OnTaxNumberFieldEnter(it)) },
                                    Modifier
                                        .fillMaxWidth(.5f)
                                        .padding(end = 5.dp),
                                    label = { Text("NIP") },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Next
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onSearch = {
                                            showText = true
                                        }
                                    ),
//                                    trailingIcon = {
//                                        if (state.queryParameters.taxNumber.isNotBlank()) {
//                                            Icon(Icons.Default.Clear,
//                                                contentDescription = "clear text",
//                                                modifier = Modifier
//                                                    .clickable {
//                                                        onAction(
//                                                            SearchAction.OnClearFieldIconClick(
//                                                                SearchViewModel.Field.TAX_NUMBER
//                                                            )
//                                                        )
//                                                    }
//                                            )
//                                        }
//                                    }
                                )

                                OutlinedTextField(
                                    value = state.queryParameters.businessRegistryNumber,
                                    onValueChange = {
                                        onAction(
                                            SearchAction.OnBusinessRegistryNumberFieldEnter(
                                                it
                                            )
                                        )
                                    },
                                    label = { Text("REGON") },
                                    modifier = Modifier
//                                            .padding(0.dp),
//                                            .fillMaxHeight()
                                        .fillMaxWidth()
                                        .padding(start = 5.dp),
                                    keyboardOptions = KeyboardOptions(
//                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Next
                                    ),
//                                    trailingIcon = {
//                                        if (state.queryParameters.businessRegistryNumber.isNotBlank()) {
//                                            Icon(Icons.Default.Clear,
//                                                contentDescription = "clear text",
//                                                modifier = Modifier
//                                                    .clickable {
//                                                        onAction(
//                                                            SearchAction.OnClearFieldIconClick(
//                                                                SearchViewModel.Field.BUSINESS_REGISTRY_NUMBER
//                                                            )
//                                                        )
//                                                    }
//                                            )
//                                        }
//                                    }
                                )
                            }
//
                            Row(
                                Modifier
//                                    .border(1.dp, Color.Red)
                                    .padding(bottom = 5.dp)
                                    .fillMaxWidth(),
                            ) {
                                OutlinedTextField(
                                    value = state.queryParameters.name,
                                    onValueChange = { onAction(SearchAction.OnNameFieldEnter(it)) },
                                    label = { Text("NAZWA") },
                                    modifier = Modifier
//                                            .padding(0.dp),
//                                            .fillMaxHeight()
                                        .fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions(
//                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Next
                                    ),
//                                    trailingIcon = {
//                                        if (state.queryParameters.name.isNotBlank()) {
//                                            Icon(Icons.Default.Clear,
//                                                contentDescription = "clear text",
//                                                modifier = Modifier
//                                                    .clickable {
//                                                        onAction(
//                                                            SearchAction.OnClearFieldIconClick(
//                                                                SearchViewModel.Field.NAME
//                                                            )
//                                                        )
//                                                    }
//                                            )
//                                        }
//                                    }
                                )
                            }

                            Row(
                                Modifier
//                                    .border(1.dp, Color.Red)
                                    .padding(bottom = 5.dp)
                                    .fillMaxWidth(),
                            ) {
                                OutlinedTextField(
                                    value = state.queryParameters.firstName,
                                    onValueChange = { onAction(SearchAction.OnFirstNameFieldEnter(it)) },
                                    Modifier
                                        .fillMaxWidth(.5f)
                                        .padding(end = 5.dp),
                                    label = { Text("IMIE") },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Next
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onSearch = {
                                            showText = true
                                        }
                                    ),
////                                    trailingIcon = {
////                                        if (state.queryParameters.firstName.isNotBlank()) {
////                                            Icon(Icons.Default.Clear,
////                                                contentDescription = "clear text",
////                                                modifier = Modifier
////                                                    .clickable {
////                                                        onAction(
////                                                            SearchAction.OnClearFieldIconClick(
////                                                                SearchViewModel.Field.FIRST_NAME
////                                                            )
////                                                        )
////                                                    }
////                                            )
////                                        }
////                                    }
                                )
////
                                OutlinedTextField(
                                    value = state.queryParameters.lastName,
                                    onValueChange = { onAction(SearchAction.OnLastNameFieldEnter(it)) },
                                    label = { Text("NAZWISKO") },
                                    modifier = Modifier
//                                            .padding(0.dp),
//                                            .fillMaxHeight()
                                        .fillMaxWidth()
                                        .padding(start = 5.dp),
                                    keyboardOptions = KeyboardOptions(
//                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Next
                                    ),
////                                    trailingIcon = {
////                                        if (state.queryParameters.lastName.isNotBlank()) {
////                                            Icon(Icons.Default.Clear,
////                                                contentDescription = "clear text",
////                                                modifier = Modifier
////                                                    .clickable {
////                                                        onAction(
////                                                            SearchAction.OnClearFieldIconClick(
////                                                                SearchViewModel.Field.LAST_NAME
////                                                            )
////                                                        )
////                                                    }
////                                            )
////                                        }
////                                    }
                                )
                            }
//
                            Row(
                                Modifier
//                                    .border(1.dp, Color.Red)
//                                    .padding(bottom = 5.dp)
                                    .fillMaxWidth(),
                            ) {
                                FilledIconButton(
                                    onClick = {
                                        openBottomSheet = false
                                        onAction(SearchAction.OnSearchContractorClick)
                                    },
                                    Modifier
                                        .fillMaxWidth()
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
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

@Composable
@Preview
private fun TextWithDescription(
    fieldName: String = "Nazwa",
    fieldText: String = "Nazwa Firmy",
    bottomSpace: Dp = 2.dp,
    occupyWidth: Float = 1f
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(occupyWidth)
            .padding(bottom = bottomSpace),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = fieldName,
            fontSize = 11.sp,
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = fieldText.ifEmpty { "-" },
            fontSize = 18.sp,
        )
    }
}
