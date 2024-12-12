package com.example.firmly.contractors.presentation.contractor_detail

interface ContractorDetailEvent {
    data class Error(val error: String): ContractorDetailEvent
    data class Success(val message: String): ContractorDetailEvent
}