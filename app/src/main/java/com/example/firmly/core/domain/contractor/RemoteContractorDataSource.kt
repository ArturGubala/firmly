package com.example.firmly.core.domain.contractor

import com.example.firmly.core.domain.util.DataError
import com.example.firmly.core.domain.util.Result

interface RemoteContractorDataSource {
    suspend fun getContractorList(queryParameters: Map<String, Any>): Result<List<ContractorListItem>, DataError.Network>
    suspend fun getContractor(id: String): Result<List<ContractorDetail>, DataError.Network>
}
