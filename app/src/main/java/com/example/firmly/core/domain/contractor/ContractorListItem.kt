package com.example.firmly.core.domain.contractor

data class ContractorListItem(
    val id: String,
    val name: String,
    val street: String?,
    val building: String?,
    val apartment: String?,
    val city: String?,
    val postalCode: String?,
    val taxNumber: String,
    val buisnessRegistryNumber: String
)
