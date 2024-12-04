package com.example.firmly.contractors.presentation.contractor_list

interface ContractorListAction {
    data class OnContractorCardClick(val contractorId: String) : ContractorListAction
}
