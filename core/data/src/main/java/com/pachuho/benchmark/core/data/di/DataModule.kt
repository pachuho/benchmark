package com.pachuho.benchmark.core.data.di

import com.pachuho.benchmark.core.data.api.JHApi
import com.pachuho.benchmark.core.data.impl.DeviceRepositoryImpl
import com.pachuho.benchmark.core.data.impl.UserRepositoryImpl
import com.pachuho.benchmark.core.datastore.datasource.AuthTokenPreferencesDataSource
import com.pachuho.benchmark.core.datastore.datasource.FirebaseTokenPreferencesDataSource
import com.pachuho.benchmark.core.domain.repository.DeviceRepository
import com.pachuho.benchmark.core.domain.repository.UserRepository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object DataModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        authApi: JHApi,
        authDataSource: AuthTokenPreferencesDataSource,
        firebaseDataSource: FirebaseTokenPreferencesDataSource
    ): UserRepository = UserRepositoryImpl(authApi, authDataSource, firebaseDataSource)

    @Provides
    @Singleton
    fun provideDeviceRepository(
        authApi: JHApi
    ): DeviceRepository = DeviceRepositoryImpl(authApi)
}