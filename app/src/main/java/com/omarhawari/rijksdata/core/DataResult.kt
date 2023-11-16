package com.omarhawari.rijksdata.core

import android.provider.ContactsContract.Data
import java.lang.Exception

sealed class DataResult<out R> {
    class Success<T>(response: T) : DataResult<T>()
    class Failure(exception: Exception) : DataResult<Nothing>()
}

inline fun <R> asDataResult(block: () -> R): DataResult<R> {
    return try {
        DataResult.Success(block())
    } catch (e: Exception) {
        DataResult.Failure(e)
    }
}