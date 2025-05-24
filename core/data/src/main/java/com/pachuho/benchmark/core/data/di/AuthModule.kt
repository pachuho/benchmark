package com.pachuho.benchmark.core.data.di

import com.pachuho.benchmark.core.data.auth.AuthTokenProvider
import com.pachuho.benchmark.core.data.auth.AuthTokenProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    abstract fun bindAuthTokenProvider(
        impl: AuthTokenProviderImpl
    ): AuthTokenProvider
}