package com.example.firmly.contractors.presentation.contractor_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firmly.core.domain.contractor.LocalContractorDataSource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContractorListViewModel(
    private val localContractorDataSource: LocalContractorDataSource,
): ViewModel() {

    private val _state = MutableStateFlow(ContractorListState())
    val state = _state
        .onStart { getSavedContractors() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            ContractorListState(),
        )

    private val eventChannel = Channel<ContractorListEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: ContractorListAction) {
        when(action) {
            is ContractorListAction.OnContractorClick -> {
                viewModelScope.launch {
                    eventChannel.send(ContractorListEvent.NavigateToDetail(action.contractorId))
                }
            }
        }
    }

    private fun getSavedContractors() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            val contractors = localContractorDataSource.getContractors().first()
            _state.value = _state.value.copy(
                contractors = contractors
            )

            _state.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }
}
