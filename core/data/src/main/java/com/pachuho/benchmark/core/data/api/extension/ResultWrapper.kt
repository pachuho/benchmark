package com.pachuho.benchmark.core.data.api.extension

import com.pachuho.benchmark.core.domain.error.ErrorMapper
import com.pachuho.benchmark.core.model.ResultWrapper

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
    return try {
        ResultWrapper.Success(apiCall())
    } catch (e: Exception) {
        ResultWrapper.Error(ErrorMapper.fromThrowable(e.cause))
    }
}