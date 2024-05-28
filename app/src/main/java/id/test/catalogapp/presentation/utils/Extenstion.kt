package id.test.catalogapp.presentation.utils

import id.test.catalogapp.domain.utils.DataState

fun <T> DataState<T>.asUiState(): UiState<T> {
    return when (this) {
        is DataState.Success -> UiState.Success(this.data)
        is DataState.Error -> {
            val message = this.error.localizedMessage.orEmpty().ifEmpty {
                "Terjadi Kesalahan"
            }
            println("cek message :$message")
            UiState.Error(message)
        }
    }
}
