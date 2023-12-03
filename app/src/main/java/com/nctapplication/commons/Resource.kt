package com.nctapplication.commons


sealed class Resource<out T>(val data: T? = null, val message: String? = null) {
    class Loading<T> : Resource<T>()
    class Success<T> (data: T?= null) : Resource<T>(data= data)
    class Error<T>(data: T? = null, message: String? = null) : Resource<T>(data, message)
}
