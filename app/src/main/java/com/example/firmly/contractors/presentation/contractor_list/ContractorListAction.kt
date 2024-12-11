package com.example.firmly.contractors.presentation.contractor_list

interface ContractorListAction {
    data class OnContractorClick(val contractorId: String) : ContractorListAction
}
