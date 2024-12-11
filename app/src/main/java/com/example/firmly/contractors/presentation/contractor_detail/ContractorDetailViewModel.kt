package com.example.firmly.contractors.presentation.contractor_detail

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

class ContractorDetailViewModel(
    contractorId: String,
    private val localContractorDataSource: LocalContractorDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(ContractorDetailState())
    val state = _state
        .onStart { getContractorDetails(contractorId) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            ContractorDetailState(),
        )

    private val eventChannel = Channel<ContractorDetailEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: ContractorDetailAction) {
        when(action) {
        }
    }

    private fun getContractorDetails(contractorId: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            val contractor = localContractorDataSource.getContractorById(contractorId).first()
            _state.value = _state.value.copy(
                contractor = contractor
            )

            _state.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }
}