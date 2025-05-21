package com.pachuho.benchmark.core.data.api.extension

import com.pachuho.benchmark.core.model.ResultWrapper
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
    return try {
        ResultWrapper.Success(apiCall())
    } catch (e: retrofit2.HttpException) {
        ResultWrapper.Error(e.code(), e.message())
    } catch (e: IOException) {
        ResultWrapper.NetworkError
    } catch (e: Exception) {
        ResultWrapper.Error(null, e.message)
    }
}