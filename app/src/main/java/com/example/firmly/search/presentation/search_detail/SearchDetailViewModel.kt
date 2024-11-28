package com.example.firmly.search.presentation.search_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firmly.core.domain.contractor.RemoteContractorDataSource
import com.example.firmly.core.domain.util.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SearchDetailViewModel(
    contractorId: String,
    private val remoteContractorDataSource: RemoteContractorDataSource
) : ViewModel() {

    init {
        getContractorDetails(contractorId)
    }

    var state by mutableStateOf(SearchDetailState())
        private set

    private val eventChannel = Channel<SearchDetailEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: SearchDetailAction) {}

    private fun getContractorDetails(contractorId: String) {
        viewModelScope.launch {
            val result = remoteContractorDataSource.getContractor(contractorId)

            when(result) {
                is Result.Error -> {
                    state = state.copy(
                        contractor = null
                    )
                    eventChannel.send(SearchDetailEvent.Error(result.error.toString()))
                }
                is Result.Success -> {
                    state = state.copy(
                        contractor = result.data.first()
                    )
                }
            }
        }
    }
}
