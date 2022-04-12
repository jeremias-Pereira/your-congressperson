package com.jpgsolution.youcongressperson.di

import com.jpgsolution.youcongressperson.model.datasource.remote.RemoteDataSource
import dagger.Component

@Component
interface DataSourceComponent {
    fun remoteDataSource(): RemoteDataSource
}