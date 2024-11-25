package com.example.firmly.search.presentation

interface SearchEvent {
    data class Error(val error: String): SearchEvent
}
