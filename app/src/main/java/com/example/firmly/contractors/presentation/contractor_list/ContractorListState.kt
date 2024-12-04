package com.example.firmly.contractors.presentation.contractor_list

import com.example.firmly.core.domain.contractor.ContractorListItem

data class ContractorListState(
    val contractors: List<ContractorListItem> = emptyList(),
    val isLoading: Boolean = false,
)
