package com.example.firmly.core.datastore

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val numberOfSavedTemporaryContractors: Int = 5,
)
