package com.example.firmly.settings

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firmly.core.datastore.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val userPreferencesDataSource: DataStore<UserPreferences>
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state
        .onStart { getUserPreferences() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            SettingsState(),
        )

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.OnNumberOfSavedTemporaryContractorsChange -> {
                viewModelScope.launch {
                    userPreferencesDataSource.updateData {
                        it.copy(
                            numberOfSavedTemporaryContractors = action.numberOfSavedTemporaryContractors)
                    }
                }
            }
        }
    }

    private fun getUserPreferences() {
        viewModelScope.launch {
            val userPreferences = userPreferencesDataSource.data.first()

            _state.update {
                it.copy(
                    numberOfSavedTemporaryContractors = userPreferences.numberOfSavedTemporaryContractors,
                )
            }
        }
    }
}