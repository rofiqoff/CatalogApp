package id.test.catalogapp.domain.utils

/**
 * This [DataState] wrapper class is used to mapping response data from repository layer
 * [Success] class to wrap data if return success
 * [Error] class to wrap error state with exception
 * */
sealed interface DataState<out T> {
    data class Success<T>(val data: T) : DataState<T>
    data class Error(val error: Throwable) : DataState<Nothing>
}
