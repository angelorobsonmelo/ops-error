package com.angelorobson.monitorerrorapp.utils

/**
 * Mapped API results, used by [SafeApiCaller]
 */
sealed class Result<out T> {
    /**
     * API call was successful, no errors, returning the desired [value]
     */
    data class Success<out T>(val value: T) : Result<T>()

    abstract class Error(
        open val code: Int? = null,
        open val exception: Exception? = null
    ) : Result<Nothing>()

    /**
     * An [retrofit2.HttpException] was thrown, the error [code] and the deserialized backend [error] will be returned
     */
    data class BackendError(
        override val code: Int? = null,
    ) : Error()

    /**
     * A generic exception was thrown
     */
    data class GenericError(override val exception: Exception? = null) : Error()

    /**
     * An [java.io.IOException] was thrown
     */
    object NetworkError : Error()

}

inline fun <T : Any> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
    if (this is Result.Success) {
        action(this.value)
    }
    return this
}

inline fun <T : Any> Result<T>.onError(action: (Result.Error) -> Unit): Result<T> {
    if (this is Result.Error) {
        action(this)
    }
    return this
}

inline fun <T : Any> Result<T>.onFinally(action: () -> Unit) {
    action()
}