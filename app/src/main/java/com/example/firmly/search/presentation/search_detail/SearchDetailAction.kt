package com.example.firmly.search.presentation.search_detail

sealed interface SearchDetailAction {
    data object OnAddContractorClick: SearchDetailAction
}
