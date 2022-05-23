package com.example.project1.di.view_model

import com.example.project1.ui.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { MainViewModel(app = get(), feedsRepository = get()) }
}
