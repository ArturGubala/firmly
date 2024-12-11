package com.example.firmly.contractors.presentation.contractor_detail

import com.example.firmly.contractors.presentation.contractor_list.ContractorListEvent

interface ContractorDetailEvent {
    data class Error(val error: String): ContractorListEvent
}