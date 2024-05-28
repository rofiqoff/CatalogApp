package id.test.catalogapp.data.utils

sealed interface DataState<out T> {
    data class Success<T>(val data: T) : DataState<T>
    data class Error(val error: Throwable) : DataState<Nothing>
}
