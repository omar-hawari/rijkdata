package com.omarhawari.rijksdata.core

sealed class DataResult<out R> {
    class Success<T>(val response: T) : DataResult<T>()
    class Failure(val exception: Exception) : DataResult<Nothing>()
}

inline fun <R> asDataResult(block: () -> R): DataResult<R> {
    return try {
        DataResult.Success(block())
    } catch (e: Exception) {
        DataResult.Failure(e)
    }
}