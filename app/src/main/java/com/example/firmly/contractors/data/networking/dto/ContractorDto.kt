@file:OptIn(ExperimentalSerializationApi::class)

package com.example.firmly.contractors.data.networking.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class SingleContractorResponse(
    @JsonNames("firma") val contractor: List<ContractorDto>
)

@Serializable
data class MultipleContractorsResponse(
    @JsonNames("firmy") val contractors: List<ContractorDto>
)

@Serializable
data class ContractorDto(
    val id: String,
    @JsonNames("nazwa") val name: String,
    @JsonNames("adresDzialalnosci") val companyAddress: CompanyDetailAddress?,
    @JsonNames("wlasciciel") val owner: OwnerDetail,
    @JsonNames("dataRozpoczecia") val startDate: String,
    @JsonNames("status") val state: String,
    @JsonNames("telefon") val phone: String?
)

@Serializable
data class CompanyDetailAddress(
    @JsonNames("ulica") val street: String?,
    @JsonNames("budynek") val building: String?,
    @JsonNames("lokal") val apartment: String?,
    @JsonNames("miasto") val city: String?,
    @JsonNames("wojewodztwo") val province: String?,
    @JsonNames("powiat") val district: String?,
    @JsonNames("gmina") val parish: String?,
    @JsonNames("kraj") val country: String?,
    @JsonNames("kod") val postalCode: String?
)

@Serializable
data class OwnerDetail(
    @JsonNames("imie") val firstName: String?,
    @JsonNames("nazwisko") val lastName: String?,
    @JsonNames("nip") val taxNumber: String,
    @JsonNames("regon") val buisnessRegistryNumber: String
)
