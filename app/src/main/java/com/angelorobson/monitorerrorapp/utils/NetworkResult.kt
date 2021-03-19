package com.angelorobson.monitorerrorapp.utils


sealed class NetworkResult<T> {
    data class Success<T>(val data: T?) : NetworkResult<T>()
    data class Error<T>(val message: String?) : NetworkResult<T>()
    data class Loading<T>(val nothing: Nothing? = null) : NetworkResult<T>()
}