package com.example.firmly.search.presentation.search_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firmly.core.domain.contractor.LocalContractorDataSource
import com.example.firmly.core.domain.contractor.RemoteContractorDataSource
import com.example.firmly.core.domain.util.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchDetailViewModel(
    contractorId: String,
    private val remoteContractorDataSource: RemoteContractorDataSource,
    private val localContractorDataSource: LocalContractorDataSource
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
                saveContractor(action.isTemporary)
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

    // TODO: split method for two? Now after back click saved contractor from non temporary became
    //  temporary. Need to check some things while trying to save temporary contractor.
    private fun saveContractor(isTemporary: Boolean = false) {
        viewModelScope.launch {
            if (state.value.contractor != null) {
                val contractor = state.value.contractor!!.copy(temporary = isTemporary)
                val result = localContractorDataSource.upsertContractor(contractor)

                when(result) {
                    is Result.Error -> {
                        eventChannel.send(SearchDetailEvent.Error(result.error.toString()))
                    }
                    is Result.Success -> {
                        if (!isTemporary){
                            eventChannel.send(SearchDetailEvent.Success("Kontrahent pomyślnie zapisany"))
                        }
                    }
                }
            }
        }
    }
}
