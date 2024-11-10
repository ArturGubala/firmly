package com.example.firmly.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ContractorEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val street: String?,
    val building: String?,
    val apartment: String?,
    val city: String?,
    val district: String?,
    val parish: String?,
    val country: String?,
    val postalCode: String?,
    val firstName: String?,
    val lastName: String?,
    val taxNumber: String,
    val buisnessRegistryNumber: String,
    val startDate: String,
    val state: String,
    val phone: String?,
    val temporary: Short,
//    val creationDate: Date
)
