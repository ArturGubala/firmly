package com.example.firmly.search.presentation.search_list

interface SearchListEvent {
    data class Error(val error: String): SearchListEvent
    data class NavigateToDetail(val contractorId: String) : SearchListEvent
}
