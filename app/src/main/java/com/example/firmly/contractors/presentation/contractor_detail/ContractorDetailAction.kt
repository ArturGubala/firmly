package com.example.firmly.contractors.presentation.contractor_detail

interface ContractorDetailAction {
    data object OnAddContractorClick: ContractorDetailAction
    data class OnDeleteContractorClick(val contractorId: String): ContractorDetailAction
    data class OnUpdateContractorClick(val contractorId: String): ContractorDetailAction
}