package com.example.firmly.contractors.data.di

import com.example.firmly.core.domain.contractor.RemoteContractorDataSource
import com.example.firmly.contractors.data.networking.KtorRemoteContractorDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val contractorModule = module {
    singleOf(::KtorRemoteContractorDataSource).bind<RemoteContractorDataSource>()
}
