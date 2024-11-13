package com.example.firmly.contractors.data.networking

import com.example.firmly.contractors.data.mappers.toContractorDetail
import com.example.firmly.contractors.data.mappers.toContractorListItem
import com.example.firmly.contractors.data.networking.dto.MultipleContractorsResponse
import com.example.firmly.contractors.data.networking.dto.SingleContractorResponse
import com.example.firmly.core.data.networking.get
import com.example.firmly.core.domain.contractor.ContractorDetail
import com.example.firmly.core.domain.contractor.ContractorListItem
import com.example.firmly.core.domain.contractor.RemoteContractorDataSource
import com.example.firmly.core.domain.util.DataError
import com.example.firmly.core.domain.util.Result
import com.example.firmly.core.domain.util.map
import io.ktor.client.HttpClient

class KtorRemoteContractorDataSource(
    private val httpClient: HttpClient
): RemoteContractorDataSource {
    override suspend fun getContractorList(queryParameters: Map<String, Any>): Result<List<ContractorListItem>, DataError.Network> {
        return httpClient.get<MultipleContractorsResponse>(
            route = "/firmy",
            queryParameters
        ).map { multipleContractorsResponse ->
            multipleContractorsResponse.contractors.map { it.toContractorListItem() }
        }
    }

    override suspend fun getContractor(id: String): Result<List<ContractorDetail>, DataError.Network> {
        return httpClient.get<SingleContractorResponse>(
            route = "/firma",
            queryParameters = mapOf("ids" to id)
        ).map { singleContractorResponse ->
            singleContractorResponse.contractor.map { contractor ->
                contractor.toContractorDetail()
            }
        }
    }
}
