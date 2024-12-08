package com.example.firmly.contractors.presentation.contractor_detail

import com.example.firmly.core.domain.contractor.ContractorDetail

data class ContractorDetailState(
    val contractor: ContractorDetail? = null,
    val isLoading: Boolean = false,
)
