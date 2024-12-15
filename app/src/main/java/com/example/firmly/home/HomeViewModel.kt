package com.example.firmly.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firmly.core.domain.contractor.LocalContractorDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val localContractorDataSource: LocalContractorDataSource,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .onStart { getContractors() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            HomeState(),
        )

    private fun getContractors() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                )
            }

            val temporaryContractors = localContractorDataSource
                .getContractorByType(type = true, numberOfResults = 5)
                .first()
            val savedContractors = localContractorDataSource
                .getContractorByType(type = false, numberOfResults = 5)
                .first()

            _state.update {
                it.copy(
                    temporaryContractors = temporaryContractors,
                    savedContractors = savedContractors,
                    isLoading = false,
                )
            }
        }
    }
}
