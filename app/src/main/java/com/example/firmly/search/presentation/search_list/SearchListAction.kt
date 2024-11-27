package com.example.firmly.search.presentation.search_list

sealed interface SearchListAction {
    data object OnSearchContractorClick: SearchListAction
    data class OnNameFieldEnter(val name: String) : SearchListAction
    data class OnCityFieldEnter(val city: String) : SearchListAction
    data class OnFirstNameFieldEnter(val firstName: String) : SearchListAction
    data class OnLastNameFieldEnter(val lastName: String) : SearchListAction
    data class OnTaxNumberFieldEnter(val taxNumber: String) : SearchListAction
    data class OnBusinessRegistryNumberFieldEnter(val businessRegistryNumber: String) : SearchListAction
    data class OnClearFieldIconClick(val field: SearchListViewModel.Field) : SearchListAction
    data class OnContractorCardClick(val contractorId: String) : SearchListAction
}
