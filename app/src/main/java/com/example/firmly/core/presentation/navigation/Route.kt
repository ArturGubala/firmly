package com.example.firmly.core.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Home : Route
    @Serializable
    data object Contractors : Route
    @Serializable
    data object Search : Route
    @Serializable
    data object Settings : Route
}
