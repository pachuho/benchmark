package com.pachuho.benchmark.core.eventbus.di

import com.pachuho.benchmark.core.eventbus.EventBus
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EventModule {
    @Provides
    @Singleton
    fun provideEventBus(): EventBus = EventBus()
}