package com.example.firmly.search.presentation.search_list

import com.example.firmly.core.domain.util.DataError

interface SearchListEvent {
    data class Error(val error: DataError): SearchListEvent
    data class NavigateToDetail(val contractorId: String) : SearchListEvent
}
