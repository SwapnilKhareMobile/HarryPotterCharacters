package com.sw.sample.data.di

import com.sw.sample.data.repo.CharRepository
import com.sw.sample.data.repo.CharRepositoryImpl
import com.sw.sample.data.source.remote.CharRemoteDataSource
import com.sw.sample.data.source.remote.CharRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DataModule {

    @Binds
    abstract fun bindCharRepository(impl: CharRepositoryImpl): CharRepository

    @Binds
    abstract fun bindCharRemoteDataSource(impl: CharRemoteDataSourceImpl): CharRemoteDataSource


}