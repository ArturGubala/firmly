package com.example.firmly.core.database.mappers

import com.example.firmly.core.database.entity.ContractorEntity
import com.example.firmly.core.domain.contractor.ContractorDetail
import com.example.firmly.core.domain.contractor.ContractorListItem

fun ContractorEntity.toContractorDetail(): ContractorDetail {
    return ContractorDetail(
        id = id,
        name = name,
        street = street,
        building = building,
        apartment = apartment,
        city = city,
        county = county,
        municipality = municipality,
        province = province,
        country = country,
        postalCode = postalCode,
        firstName = firstName,
        lastName = lastName,
        taxNumber = taxNumber,
        buisnessRegistryNumber = buisnessRegistryNumber,
        startDate = startDate,
        state = state,
        phone = phone,
        temporary = temporary,
        creationDate = creationDate
    )
}

fun ContractorDetail.toContractorEntity(): ContractorEntity {
    return ContractorEntity(
        id = id,
        name = name,
        street = street,
        building = building,
        apartment = apartment,
        city = city,
        county = county,
        municipality = municipality,
        province = province,
        country = country,
        postalCode = postalCode,
        firstName = firstName,
        lastName = lastName,
        taxNumber = taxNumber,
        buisnessRegistryNumber = buisnessRegistryNumber,
        startDate = startDate,
        state = state,
        phone = phone,
        temporary = temporary,
        creationDate = creationDate
    )
}

fun ContractorEntity.toContractorListItem(): ContractorListItem {
    return ContractorListItem(
        id = id,
        name = name,
        street = street,
        building = building,
        apartment = apartment,
        city = city,
        postalCode = postalCode,
        taxNumber = taxNumber,
        buisnessRegistryNumber = buisnessRegistryNumber,
        temporary = temporary
    )
}
