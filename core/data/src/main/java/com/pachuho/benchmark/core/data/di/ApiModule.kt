package com.pachuho.benchmark.core.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.pachuho.benchmark.core.data.BuildConfig
import com.pachuho.benchmark.core.data.api.JHApi
import com.pachuho.benchmark.core.data.auth.TokenAuthenticator
import com.pachuho.benchmark.core.data.interceptor.AuthInterceptor
import com.pachuho.benchmark.core.data.interceptor.LoggerInterceptor
import com.pachuho.benchmark.core.data.ws.BenchmarkWebSocketImpl
import com.pachuho.benchmark.core.domain.socket.BenchmarkWebSocket
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Provides
    @Singleton
    fun provideOkhttpClient(
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator,
        loggerInterceptor: LoggerInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor(loggerInterceptor).setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(authInterceptor)
        .authenticator(tokenAuthenticator)
        .build()

    @Provides
    @Singleton
    fun provideConverterFactory(
        json: Json,
    ): Converter.Factory {
        return json.asConverterFactory("application/json".toMediaType())
    }

    @Provides
    @Singleton
    fun provideJHApi(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): JHApi = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(converterFactory)
        .client(okHttpClient).build()
        .create(JHApi::class.java)

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun provideWebSocketDataSource(
        client: OkHttpClient
    ): BenchmarkWebSocket = BenchmarkWebSocketImpl(client)
}