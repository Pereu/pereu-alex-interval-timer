package com.pereu.intervaltimer.util

sealed class Resource<out T> {
    data object Loading : Resource<Nothing>()
    class Success<out T>(val data: T) : Resource<T>()
    class Error(val throwable: Throwable) : Resource<Nothing>()
}

fun <T> Resource<T>.getDataOrNull(): T? = (this as? Resource.Success)?.data
fun <T> Resource<T>.isLoadingState(): Boolean = this is Resource.Loading