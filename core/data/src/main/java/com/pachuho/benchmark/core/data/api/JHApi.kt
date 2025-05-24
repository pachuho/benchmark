package com.pachuho.benchmark.core.data.api

import com.pachuho.benchmark.core.data.api.model.request.LoginRequest
import com.pachuho.benchmark.core.data.api.model.request.ReissueRequest
import com.pachuho.benchmark.core.data.api.model.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

internal interface JHApi {
    @POST("/api/account/test/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("/api/account/test/reissue")
    suspend fun reissue(
        @Body request: ReissueRequest
    ): LoginResponse
}