package com.example.firmly.search.presentation.search_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firmly.core.domain.contractor.ContractorQueryParameters
import com.example.firmly.core.domain.contractor.RemoteContractorDataSource
import com.example.firmly.core.domain.contractor.toMap
import com.example.firmly.core.domain.util.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SearchListViewModel(
    private val remoteContractorDataSource: RemoteContractorDataSource
): ViewModel() {

    var state by mutableStateOf(SearchListState())
        private set

    private val eventChannel = Channel<SearchListEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: SearchListAction) {
        when(action) {
            is SearchListAction.OnSearchContractorClick -> {
                search()
            }
            is SearchListAction.OnNameFieldEnter -> {
                if (action.name.matches(Regex("^[\\w\\s\\W\\SąćęłńóśźżĄĆĘŁŃÓŚŹŻ]*$"))) {

                    state = state.copy(queryParameters = ContractorQueryParameters(
                        name = action.name,
                        city = state.queryParameters.city,
                        firstName = state.queryParameters.firstName,
                        lastName = state.queryParameters.lastName,
                        taxNumber = state.queryParameters.taxNumber,
                        businessRegistryNumber = state.queryParameters.businessRegistryNumber,
                        status = "AKTYWNY"
                    )
                    )
                }
            }
            is SearchListAction.OnCityFieldEnter -> {
                if (action.city.matches(Regex("^[\\w\\s\\W\\SąćęłńóśźżĄĆĘŁŃÓŚŹŻ]*$"))) {
                    state = state.copy(queryParameters = ContractorQueryParameters(
                        name = state.queryParameters.name,
                        city = action.city,
                        firstName = state.queryParameters.firstName,
                        lastName = state.queryParameters.lastName,
                        taxNumber = state.queryParameters.taxNumber,
                        businessRegistryNumber = state.queryParameters.businessRegistryNumber,
                        status = "AKTYWNY"
                    )
                    )
                }
            }
            is SearchListAction.OnFirstNameFieldEnter -> {
                val firstNameWithOutWhiteChars = action.firstName.trim()
                if (firstNameWithOutWhiteChars.matches(Regex("^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]*$"))) {
                    state = state.copy(queryParameters = ContractorQueryParameters(
                        name = state.queryParameters.name,
                        city = state.queryParameters.city,
                        firstName = firstNameWithOutWhiteChars,
                        lastName = state.queryParameters.lastName,
                        taxNumber = state.queryParameters.taxNumber,
                        businessRegistryNumber = state.queryParameters.businessRegistryNumber,
                        status = "AKTYWNY"
                    )
                    )
                }
            }
            is SearchListAction.OnLastNameFieldEnter -> {
                if (action.lastName.replace(" ", "").matches(Regex("^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ\\-]*$"))) {
                    state = state.copy(queryParameters = ContractorQueryParameters(
                        name = state.queryParameters.name,
                        city = state.queryParameters.city,
                        firstName = state.queryParameters.firstName,
                        lastName = action.lastName,
                        taxNumber = state.queryParameters.taxNumber,
                        businessRegistryNumber = state.queryParameters.businessRegistryNumber,
                        status = "AKTYWNY"
                    )
                    )
                }
            }
            is SearchListAction.OnTaxNumberFieldEnter -> {
                if (action.taxNumber.length <= 11 && action.taxNumber.isDigitsOnly()) {
                    state = state.copy(queryParameters = ContractorQueryParameters(
                        name = state.queryParameters.name,
                        city = state.queryParameters.city,
                        firstName = state.queryParameters.firstName,
                        lastName = state.queryParameters.lastName,
                        taxNumber = action.taxNumber,
                        businessRegistryNumber = state.queryParameters.businessRegistryNumber,
                        status = "AKTYWNY"
                    )
                    )
                }
            }
            is SearchListAction.OnBusinessRegistryNumberFieldEnter -> {
                if (action.businessRegistryNumber.length <= 9 && action.businessRegistryNumber.isDigitsOnly()) {
                    state = state.copy(queryParameters = ContractorQueryParameters(
                        name = state.queryParameters.name,
                        city = state.queryParameters.city,
                        firstName = state.queryParameters.firstName,
                        lastName = state.queryParameters.lastName,
                        taxNumber = state.queryParameters.taxNumber,
                        businessRegistryNumber = action.businessRegistryNumber,
                        status = "AKTYWNY"
                    )
                    )
                }
            }
            is SearchListAction.OnContractorCardClick -> {
                viewModelScope.launch {
                    eventChannel.send(SearchListEvent.NavigateToDetail(action.contractorId))
                }
            }

            is SearchListAction.OnClearFieldIconClick -> clearField(action.field)
        }
    }

    private fun search() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = remoteContractorDataSource.getContractorList(state.queryParameters.toMap())

            when(result) {
                is Result.Error -> {
                    state = state.copy(
                        contractors = emptyList()
                    )
                    eventChannel.send(SearchListEvent.Error(result.error.toString()))
                }
                is Result.Success -> {
                    state = state.copy(
                        contractors = result.data
                    )
                }
            }

            state = state.copy(isLoading = false)
        }
    }

    fun clearField(field: Field) {
        state = state.copy(
            queryParameters = when (field) {
                Field.NAME -> state.queryParameters.copy(name = "")
                Field.CITY -> state.queryParameters.copy(city = "")
                Field.FIRST_NAME -> state.queryParameters.copy(firstName = "")
                Field.LAST_NAME -> state.queryParameters.copy(lastName = "")
                Field.TAX_NUMBER -> state.queryParameters.copy(taxNumber = "")
                Field.BUSINESS_REGISTRY_NUMBER -> state.queryParameters.copy(businessRegistryNumber = "")
                Field.STATUS -> state.queryParameters.copy(status = "")
            }
        )
    }

    enum class Field {
        NAME, CITY, FIRST_NAME, LAST_NAME, TAX_NUMBER, BUSINESS_REGISTRY_NUMBER, STATUS
    }
}
