package id.test.catalogapp.presentation.utils

/**
 * This [UiState] wrapper class is used to mapping response data from domain layer to consume in View
 * [Success] class to wrap state if return success with data collection
 * [Error] class to wrap error state with exception
 * */
sealed interface UiState<out T> {
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val errorMessage: String) : UiState<Nothing>

    data object Idle : UiState<Nothing>

    fun isSuccess(): Boolean {
        return when (this) {
            is Success -> true
            else -> false
        }
    }

    fun getDataContent(): T? {
        return when (this) {
            is Success -> data
            else -> null
        }
    }

    fun getMessageError(): String {
        return when (this) {
            is Error -> errorMessage
            else -> ""
        }
    }
}
