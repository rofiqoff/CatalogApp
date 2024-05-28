package id.test.catalogapp.domain.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

fun <T> Flow<T>.asDataState(): Flow<DataState<T>> {
    return this
        .map<T, DataState<T>> { DataState.Success(it) }
        .catch {
            val message = it.localizedMessage

            println("cek message throwable: $it")
            println("cek message asDataState: $message")
            emit(DataState.Error(it))
        }
}
