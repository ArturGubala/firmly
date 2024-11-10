package com.example.firmly.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.firmly.core.database.dao.ContractorDao
import com.example.firmly.core.database.entity.ContractorEntity

@Database(
    entities = [
        ContractorEntity::class
    ],
    version = 1
)

abstract class ContractorDatabase : RoomDatabase() {

    abstract val contractorDao: ContractorDao
}
