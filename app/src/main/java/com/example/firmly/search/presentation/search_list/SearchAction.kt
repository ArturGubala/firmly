package com.example.firmly.search.presentation.search_list

sealed interface SearchAction {
    data object OnSearchContractorClick: SearchAction
    data class OnNameFieldEnter(val name: String) : SearchAction
    data class OnCityFieldEnter(val city: String) : SearchAction
    data class OnFirstNameFieldEnter(val firstName: String) : SearchAction
    data class OnLastNameFieldEnter(val lastName: String) : SearchAction
    data class OnTaxNumberFieldEnter(val taxNumber: String) : SearchAction
    data class OnBusinessRegistryNumberFieldEnter(val businessRegistryNumber: String) : SearchAction
    data class OnClearFieldIconClick(val field: SearchViewModel.Field) : SearchAction
}