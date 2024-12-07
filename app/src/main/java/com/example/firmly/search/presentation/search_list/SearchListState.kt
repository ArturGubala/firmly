package com.example.firmly.search.presentation.search_list

import com.example.firmly.core.domain.contractor.ContractorListItem
import com.example.firmly.core.domain.contractor.ContractorQueryParameters

data class SearchListState(
    val contractors: List<ContractorListItem> = emptyList(),
    val queryParameters: ContractorQueryParameters = ContractorQueryParameters(),
    val isLoading: Boolean = false,
)
