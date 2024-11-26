package com.example.firmly.search.presentation.search_list

import com.example.firmly.core.domain.contractor.ContractorListItem
import com.example.firmly.core.domain.contractor.ContractorQueryParameters

data class SearchState(
    val contractors: List<ContractorListItem> = emptyList(),
    val queryParameters: ContractorQueryParameters = ContractorQueryParameters(),
)