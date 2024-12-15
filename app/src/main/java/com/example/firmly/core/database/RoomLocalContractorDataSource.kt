package com.example.firmly.core.database

import android.database.sqlite.SQLiteFullException
import com.example.firmly.core.database.dao.ContractorDao
import com.example.firmly.core.database.mappers.toContractorDetail
import com.example.firmly.core.database.mappers.toContractorEntity
import com.example.firmly.core.database.mappers.toContractorListItem
import com.example.firmly.core.domain.contractor.ContractorDetail
import com.example.firmly.core.domain.contractor.ContractorId
import com.example.firmly.core.domain.contractor.ContractorListItem
import com.example.firmly.core.domain.contractor.LocalContractorDataSource
import com.example.firmly.core.domain.util.DataError
import com.example.firmly.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalContractorDataSource(
    private val contractorDao: ContractorDao
): LocalContractorDataSource {

    override fun getContractors(): Flow<List<ContractorListItem>> {
        return contractorDao.getContractors()
            .map { contractorEntities ->
                contractorEntities.map { it.toContractorListItem() }
            }
    }

    override fun getContractorById(id: String): Flow<ContractorDetail?> {
        return contractorDao.getContractorById(id)
            .map { it?.toContractorDetail() }
    }

    override fun getContractorByType(
        type: Boolean,
        numberOfResults: Int
    ): Flow<List<ContractorListItem>> {
        return contractorDao.getContractorByType(type, numberOfResults)
            .map { contractorEntities ->
                contractorEntities.map { it.toContractorListItem() }
            }
    }

    override suspend fun upsertContractor(contractor: ContractorDetail): Result<ContractorId, DataError.Local> {
        return try {
            val entity = contractor.toContractorEntity()
            contractorDao.upsertContractor(entity)
            Result.Success(entity.id)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteContractor(id: String) {
        contractorDao.deleteContractor(id)
    }

    override fun getNumberOfTemporaryContractors(): Flow<Int> {
        return contractorDao.getNumberOfTemporaryContractors()
    }

    override fun getIdOfEarliestAddedTemporaryContractor(): Flow<String> {
        return contractorDao.getIdOfEarliestAddedTemporaryContractor()
    }
}
