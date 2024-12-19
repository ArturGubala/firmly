package com.example.firmly.settings.di

import com.example.firmly.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val settingsViewModelModule = module {
    viewModelOf(::SettingsViewModel)
}
