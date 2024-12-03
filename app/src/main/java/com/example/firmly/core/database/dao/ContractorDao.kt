package com.example.firmly.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.firmly.core.database.entity.ContractorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContractorDao {

    @Upsert
    suspend fun upsertContractor(contractor: ContractorEntity)

    @Query("SELECT * FROM contractorentity")
    fun getContractors(): Flow<List<ContractorEntity>>

    @Query("SELECT * FROM contractorentity WHERE id = :id")
    fun getContractorById(id: String): Flow<ContractorEntity?>

    @Query("SELECT * FROM contractorentity WHERE `temporary` = :type LIMIT :numberOfResults")
    fun getContractorByType(type: Short, numberOfResults: Int): Flow<List<ContractorEntity>>

    @Query("DELETE FROM contractorentity WHERE id = :id")
    suspend fun deleteContractor(id: String)

    @Query("SELECT COUNT(id) FROM contractorentity WHERE `temporary` = 1")
    fun getNumberOfTemporaryContractors(): Flow<Int>

    @Query("SELECT id FROM contractorentity WHERE `temporary` = 1 ORDER BY creationDate ASC LIMIT 1")
    fun getIdOfEarliestAddedTemporaryContractor(): Flow<String>
}
