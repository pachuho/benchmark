package com.pachuho.benchmark.core.model

sealed class ResultWrapper<out T> {
    data class Success<T>(val data: T) : ResultWrapper<T>()
    data class Error(val messageRes: Int) : ResultWrapper<Nothing>()
}