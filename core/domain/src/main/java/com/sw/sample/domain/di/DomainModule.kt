package com.sw.sample.domain.di

import com.sw.sample.domain.CharUseCase
import com.sw.sample.domain.GetCharUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindCharUseCase(impl: GetCharUseCase): CharUseCase

}