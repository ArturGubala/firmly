package com.example.firmly.settings

interface SettingsAction {
    data class OnNumberOfSavedTemporaryContractorsChange(val numberOfSavedTemporaryContractors: Int) : SettingsAction
}
