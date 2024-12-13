package com.example.firmly.contractors.presentation.contractor_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlin.collections.first

class ContractorDetailViewModel(
    contractorId: String,
    private val localContractorDataSource: LocalContractorDataSource,
    private val remoteContractorDataSource: RemoteContractorDataSource,
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
            is ContractorDetailAction.OnAddContractorClick -> {
                saveTemporaryContractor()
            }
            is ContractorDetailAction.OnDeleteContractorClick -> {
                deleteContractor(action.contractorId)
            }
            is ContractorDetailAction.OnUpdateContractorClick -> {
                updateContractor(action.contractorId)
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

    private fun saveTemporaryContractor() {
        viewModelScope.launch {
            val contractorToUpdate = _state.value.contractor!!.copy(temporary = false)
            localContractorDataSource.upsertContractor(contractorToUpdate)
            _state.value = _state.value.copy(
                contractor = contractorToUpdate
            )
            eventChannel.send(ContractorDetailEvent.Success("Kontrahent pomylnie zapisany"))
        }
    }

    private fun deleteContractor(contractorId: String) {
        viewModelScope.launch {
            localContractorDataSource.deleteContractor(contractorId)
            eventChannel.send(ContractorDetailEvent.Success("Kontrahent usunięty"))
        }

    }

    private fun updateContractor(contractorId: String) {
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
                    eventChannel.send(ContractorDetailEvent.Error(result.error.toString()))
                }
                is Result.Success -> {
                    val fetchedContractor = result.data.first()
                    val contractorToUpdate = fetchedContractor.copy(creationDate = System.currentTimeMillis())
                    localContractorDataSource.upsertContractor(contractorToUpdate)

                    _state.update {
                        it.copy(
                            isLoading = false,
                            contractor = contractorToUpdate
                        )
                    }
                }
            }
        }
    }
}