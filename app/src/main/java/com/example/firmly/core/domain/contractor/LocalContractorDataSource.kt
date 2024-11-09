package com.example.firmly.core.domain.contractor

import com.example.firmly.core.domain.util.DataError
import com.example.firmly.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

typealias ContractorId = String

interface LocalContractorDataSource {
    fun getContractors(): Flow<List<ContractorListItem>>
    fun getContractorByType(type: Short, numberOfResults: Int): Flow<List<ContractorListItem>>
    suspend fun upsertContractor(contractor: ContractorDetail): Result<ContractorId, DataError.Local>
    suspend fun deleteContractor(id: String)
}