package com.example.firmly.contractors.data.mappers

import com.example.firmly.contractors.data.networking.dto.ContractorDto
import com.example.firmly.contractors.data.networking.dto.MultipleContractorsResponse
import com.example.firmly.contractors.data.networking.dto.SingleContractorResponse
import com.example.firmly.core.domain.contractor.ContractorDetail
import com.example.firmly.core.domain.contractor.ContractorListItem

fun ContractorDto.toContractorListItem(): ContractorListItem {
    return ContractorListItem(
        id = id,
        name = name,
        street = companyAddress?.street,
        building = companyAddress?.building,
        apartment = companyAddress?.apartment,
        city = companyAddress?.city,
        postalCode = companyAddress?.postalCode,
        taxNumber = owner.taxNumber,
        buisnessRegistryNumber = owner.buisnessRegistryNumber
    )
}

fun ContractorDto.toContractorDetail(): ContractorDetail {
    return ContractorDetail(
        id = id,
        name = name,
        street = companyAddress?.street,
        building = companyAddress?.building,
        apartment = companyAddress?.apartment,
        city = companyAddress?.city,
        county = companyAddress?.county,
        municipality = companyAddress?.municipality,
        province = companyAddress?.province,
        country = companyAddress?.country,
        postalCode = companyAddress?.postalCode,
        firstName = owner.firstName,
        lastName = owner.lastName,
        taxNumber = owner.taxNumber,
        buisnessRegistryNumber = owner.buisnessRegistryNumber,
        startDate = startDate,
        state = state,
        phone = phone,
        temporary = 0
    )
}

fun SingleContractorResponse.toContractorDetail(): List<ContractorDetail> {
    return contractor.map { it.toContractorDetail() }
}

fun MultipleContractorsResponse.toContractorListItems(): List<ContractorListItem> {
    return contractors.map { it.toContractorListItem() }
}
