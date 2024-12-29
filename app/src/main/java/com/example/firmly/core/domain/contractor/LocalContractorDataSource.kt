package com.example.firmly.core.domain.contractor

import com.example.firmly.core.domain.util.DataError
import com.example.firmly.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

typealias ContractorId = String

interface LocalContractorDataSource {
    fun getContractors(): Flow<List<ContractorListItem>>
    fun getContractorById(id: String): Flow<ContractorDetail?>
    fun getContractorByType(type: Boolean, numberOfResults: Int): Flow<List<ContractorListItem>>
    fun getContractorsDetailByType(type: Boolean, numberOfResults: Int): Flow<List<ContractorDetail>>
    suspend fun upsertContractor(contractor: ContractorDetail): Result<ContractorId, DataError.Local>
    suspend fun deleteContractor(id: String)
    suspend fun deleteContractors(contractors: List<ContractorDetail>)
    fun getNumberOfTemporaryContractors(): Flow<Int>
    fun getIdOfEarliestAddedTemporaryContractor(): Flow<String>
}
