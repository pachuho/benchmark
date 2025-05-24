package com.pachuho.benchmark.core.eventbus.di

import com.pachuho.benchmark.core.eventbus.manager.AuthEventManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthEventModule {
    @Provides
    @Singleton
    fun provideAuthEventManager(): AuthEventManager = AuthEventManager()
}