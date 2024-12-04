package com.example.firmly.contractors.presentation.contractor_list

interface ContractorListEvent {
    data class Error(val error: String): ContractorListEvent
    data class NavigateToDetail(val contractorId: String) : ContractorListEvent
}
