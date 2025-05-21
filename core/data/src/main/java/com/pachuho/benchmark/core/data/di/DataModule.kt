package com.pachuho.benchmark.core.data.di

import com.pachuho.benchmark.core.data.api.JHApi
import com.pachuho.benchmark.core.data.impl.UserRepositoryImpl
import com.pachuho.benchmark.core.domain.repository.UserRepository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class DataModule {

    @Provides
    @Singleton
    fun provideSponsorRepository(
        authApi: JHApi
    ): UserRepository = UserRepositoryImpl(authApi)

}