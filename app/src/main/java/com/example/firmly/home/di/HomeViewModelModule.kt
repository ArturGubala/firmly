package com.example.firmly.home.di

import com.example.firmly.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val homeViewModelModule = module {
    viewModelOf(::HomeViewModel)
}
