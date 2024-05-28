package id.test.catalogapp.data.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

fun <T> Flow<T>.asDataState(): Flow<DataState<T>> {
    return this
        .map<T, DataState<T>> { DataState.Success(it) }
        .catch { emit(DataState.Error(it)) }
}
