package com.example.firmly.contractors.di

import com.example.firmly.contractors.presentation.contractor_detail.ContractorDetailViewModel
import com.example.firmly.contractors.presentation.contractor_list.ContractorListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val contractorViewModelModule = module {
    viewModelOf(::ContractorListViewModel)
    viewModel { ContractorDetailViewModel(get(), get()) }
}
