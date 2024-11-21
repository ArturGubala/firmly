package com.example.firmly.search

interface SearchEvent {
    data class Error(val error: String): SearchEvent
}
