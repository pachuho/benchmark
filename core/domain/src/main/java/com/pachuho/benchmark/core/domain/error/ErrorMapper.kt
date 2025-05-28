package com.pachuho.benchmark.core.domain.error

object ErrorMapper {
    fun fromThrowable(throwable: Throwable?): Int {
        return when (throwable) {
            is java.net.UnknownHostException -> ErrorConstants.NETWORK_DISCONNECTED
            is java.net.SocketTimeoutException -> ErrorConstants.TIMEOUT
            is retrofit2.HttpException -> {
                when (throwable.code()) {
                    401 -> ErrorConstants.UNAUTHORIZED
                    404 -> ErrorConstants.NOT_FOUND
                    in 500..599 -> ErrorConstants.SERVER_ERROR
                    else -> ErrorConstants.UNKNOWN
                }
            }
            is kotlinx.serialization.SerializationException -> ErrorConstants.JSON_PARSING
            is SecurityException -> ErrorConstants.PERMISSION_DENIED
            is java.io.FileNotFoundException -> ErrorConstants.FILE_NOT_FOUND
            is java.lang.UnsupportedOperationException -> ErrorConstants.FEATURE_NOT_SUPPORTED
            else -> ErrorConstants.UNKNOWN
        }
    }
}
