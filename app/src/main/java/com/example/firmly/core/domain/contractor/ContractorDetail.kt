package com.example.firmly.core.domain.contractor

data class ContractorDetail(
    val id: String,
    val name: String,
    val street: String?,
    val building: String?,
    val apartment: String?,
    val city: String?,
    val county: String?,
    val municipality: String?,
    val province: String?,
    val country: String?,
    val postalCode: String?,
    val firstName: String?,
    val lastName: String?,
    val taxNumber: String,
    val buisnessRegistryNumber: String,
    val startDate: String,
    val state: String,
    val phone: String?,
    val temporary: Boolean
)
