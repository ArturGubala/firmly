package com.example.firmly.core.domain.contractor

data class ContractorQueryParameters(
    val name: String = "",
    val city: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val taxNumber: String = "",
    val businessRegistryNumber: String = "",
    val status: String = "",
    val page: String = "",
    val limit: String = ""
)

fun ContractorQueryParameters.toMap(): Map<String, Any> {
    return mapOf(
        "nazwa" to name,
        "miasto" to city,
        "imie" to firstName,
        "nazwisko" to lastName,
        "nip" to taxNumber,
        "regon" to businessRegistryNumber,
        "status" to status,
        "page" to page,
        "limit" to limit
    ).filterValues { it.isNotEmpty() }
}
