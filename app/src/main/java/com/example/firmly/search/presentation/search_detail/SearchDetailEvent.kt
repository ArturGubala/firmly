package com.example.firmly.search.presentation.search_detail

interface SearchDetailEvent {
    data class Error(val error: String): SearchDetailEvent
    data class Success(val message: String): SearchDetailEvent
}
