package com.example.firmly.search.presentation.search_list

interface SearchEvent {
    data class Error(val error: String): SearchEvent
}