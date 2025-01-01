package com.example.firmly.settings

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firmly.core.datastore.UserPreferences
import com.example.firmly.core.domain.contractor.LocalContractorDataSource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val userPreferencesDataSource: DataStore<UserPreferences>,
    private val localContractorDataSource: LocalContractorDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state
        .onStart { getUserPreferences() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            SettingsState(),
        )

    private val eventChannel = Channel<SettingsEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.OnNumberOfSavedTemporaryContractorsChange -> {
                viewModelScope.launch {
                    localContractorDataSource.getContractorsDetailByType(true, 15)
                        .firstOrNull()?.let { contractors ->
                            val numberOfContractorsToDelete = contractors.size - action.numberOfSavedTemporaryContractors
                            if (numberOfContractorsToDelete > 0) {
                                val contractorsToDelete = contractors.take(numberOfContractorsToDelete)
                                localContractorDataSource.deleteContractors(contractorsToDelete)
                            }
                        }

                    userPreferencesDataSource.updateData {
                        it.copy(
                            numberOfSavedTemporaryContractors = action.numberOfSavedTemporaryContractors)
                    }

                    _state.update {
                        it.copy(
                            numberOfSavedTemporaryContractors = action.numberOfSavedTemporaryContractors,
                        )
                    }

                    eventChannel.send(SettingsEvent.Success("Pomyślnie zapisano zmiany"))
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