package com.example.firmly.core.database.di

import androidx.room.Room
import com.example.firmly.core.database.ContractorDatabase
import com.example.firmly.core.database.RoomLocalContractorDataSource
import com.example.firmly.core.domain.contractor.LocalContractorDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            ContractorDatabase::class.java,
            "firmly.db"
        ).build()
    }
    single { get<ContractorDatabase>().contractorDao }

    singleOf(::RoomLocalContractorDataSource).bind<LocalContractorDataSource>()
}
