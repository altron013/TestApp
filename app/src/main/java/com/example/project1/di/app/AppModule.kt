package com.example.project1.di.app

import com.example.project1.di.network.networkModule
import com.example.project1.di.network.repositoryModule
import com.example.project1.di.view_model.viewModelModule

val appModule = listOf(viewModelModule, networkModule, repositoryModule)
