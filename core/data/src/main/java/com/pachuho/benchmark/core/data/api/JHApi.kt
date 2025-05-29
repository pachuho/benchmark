package com.pachuho.benchmark.core.data.api

import com.pachuho.benchmark.core.data.api.model.request.DeviceControlRequest
import com.pachuho.benchmark.core.data.api.model.request.FirebaseTokenRequest
import com.pachuho.benchmark.core.data.api.model.request.LoginRequest
import com.pachuho.benchmark.core.data.api.model.request.ReissueRequest
import com.pachuho.benchmark.core.data.api.model.response.DeviceResponse
import com.pachuho.benchmark.core.data.api.model.response.LoginResponse
import com.pachuho.benchmark.core.data.api.model.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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

    @GET("/api/device/list")
    suspend fun getDevices(): List<DeviceResponse>

    @GET("/api/device/{deviceId}")
    suspend fun getDevice(
        @Path(value = "deviceId") deviceId: String,
    ): DeviceResponse

    @POST("/api/device/{deviceId}")
    suspend fun controlDevice(
        @Path(value = "deviceId") deviceId: String,
        @Body request: List<DeviceControlRequest>
    )
}