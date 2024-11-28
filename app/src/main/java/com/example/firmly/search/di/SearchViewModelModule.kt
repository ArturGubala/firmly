package com.example.firmly.search.di

import com.example.firmly.search.presentation.search_detail.SearchDetailViewModel
import com.example.firmly.search.presentation.search_list.SearchListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val searchViewModelModule = module {
    viewModelOf(::SearchListViewModel)
    viewModel { SearchDetailViewModel(get(), get()) }
}
