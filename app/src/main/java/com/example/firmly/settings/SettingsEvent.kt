package com.example.firmly.settings

interface SettingsEvent {
    data class Success(val message: String): SettingsEvent
}
