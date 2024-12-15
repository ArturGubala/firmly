package com.example.firmly.home

import com.example.firmly.core.domain.contractor.ContractorListItem

data class HomeState (
    val temporaryContractors: List<ContractorListItem> = emptyList(),
    val savedContractors: List<ContractorListItem> = emptyList(),
    val isLoading: Boolean = false
)
