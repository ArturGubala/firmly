package com.example.firmly.search.presentation.search_detail

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firmly.core.datastore.UserPreferences
import com.example.firmly.core.domain.contractor.ContractorDetail
import com.example.firmly.core.domain.contractor.LocalContractorDataSource
import com.example.firmly.core.domain.contractor.RemoteContractorDataSource
import com.example.firmly.core.domain.util.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchDetailViewModel(
    contractorId: String,
    private val remoteContractorDataSource: RemoteContractorDataSource,
    private val localContractorDataSource: LocalContractorDataSource,
    private val userPreferencesDataSource: DataStore<UserPreferences>
) : ViewModel() {

    private val _state = MutableStateFlow(SearchDetailState())
    val state = _state
        .onStart { getContractorDetails(contractorId) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            SearchDetailState(),
        )

    private val eventChannel = Channel<SearchDetailEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: SearchDetailAction) {
        when(action) {
            is SearchDetailAction.OnSaveContractorClick -> {
                saveContractor(action.isTemporary)
            }

            is SearchDetailAction.OnBackClick -> {
                saveTemporaryContractor()
            }
        }
    }

    private fun getContractorDetails(contractorId: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            val result = remoteContractorDataSource.getContractor(contractorId)

            when(result) {
                is Result.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            contractor = null
                        )
                    }
                    eventChannel.send(SearchDetailEvent.Error(result.error.toString()))
                }
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            contractor = result.data.first()
                        )
                    }
                }
            }
        }
    }

    private fun saveContractor(isTemporary: Boolean = false) {
        val contractor = state.value.contractor!!.copy(temporary = isTemporary)
        saveContractorToDb(contractor)
    }

    private fun saveTemporaryContractor() {
        viewModelScope.launch {
            val contractor = localContractorDataSource.getContractorById(state.value.contractor!!.id).first()
            if (contractor != null) {
                return@launch
            }

            var numberOfTemporaryContractors: Int = localContractorDataSource
                .getNumberOfTemporaryContractors()
                .first()

            val userPreferences = userPreferencesDataSource.data.first()
            if (numberOfTemporaryContractors == userPreferences.numberOfSavedTemporaryContractors){
                val earlierAddedTemporaryContractorId: String = localContractorDataSource
                    .getIdOfEarliestAddedTemporaryContractor()
                    .first()
                localContractorDataSource.deleteContractor(earlierAddedTemporaryContractorId)
            }

            val contractorForInsert = state.value.contractor!!.copy(temporary = true)
            saveContractorToDb(contractorForInsert)
        }
    }

    private fun saveContractorToDb(contractor: ContractorDetail) {
        viewModelScope.launch {
            if (state.value.contractor != null) {
                val result = localContractorDataSource.upsertContractor(
                    contractor.copy(creationDate = System.currentTimeMillis())
                )

                when(result) {
                    is Result.Error -> {
                        eventChannel.send(SearchDetailEvent.Error(result.error.toString()))
                    }
                    is Result.Success -> {
                        if (!contractor.temporary){
                            eventChannel.send(SearchDetailEvent.Success("Kontrahent pomyślnie zapisany"))
                        }
                    }
                }
            }
        }
    }
}
