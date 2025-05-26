package com.pachuho.benchmark.core.data.api

import com.pachuho.benchmark.core.data.api.model.request.FirebaseTokenRequest
import com.pachuho.benchmark.core.data.api.model.request.LoginRequest
import com.pachuho.benchmark.core.data.api.model.request.ReissueRequest
import com.pachuho.benchmark.core.data.api.model.response.DevicesResponse
import com.pachuho.benchmark.core.data.api.model.response.LoginResponse
import com.pachuho.benchmark.core.data.api.model.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

internal interface JHApi {
    @POST("/api/account/me")
    suspend fun getUser(): UserResponse

    @POST("/api/account/beta/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("/api/account/reissue")
    suspend fun reissue(
        @Body request: ReissueRequest
    ): LoginResponse

    @POST("/api/notification/fcm")
    suspend fun uploadFirebaseToken(
        @Body request: FirebaseTokenRequest
    )

    @DELETE("/api/notification/fcm")
    suspend fun deleteFirebaseToken(
        @Body request: FirebaseTokenRequest
    )

    @POST("/api/device/list")
    suspend fun getDevices(): DevicesResponse
}