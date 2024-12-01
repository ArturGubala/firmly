package com.example.firmly.search.presentation.search_detail

sealed interface SearchDetailAction {
    data class OnSaveContractorClick(val isTemporary: Boolean = false): SearchDetailAction
}
