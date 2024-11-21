package com.example.firmly.core.di

import com.example.firmly.core.data.networking.HttpClientFactory
import io.ktor.client.engine.cio.CIO
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
}
