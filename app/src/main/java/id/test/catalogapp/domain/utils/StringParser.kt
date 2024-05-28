package id.test.catalogapp.domain.utils

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object StringParser {

    inline fun <reified T> resourceToList(fileName: String): List<T> {
        return runCatching {
            val source = this::class.java.classLoader!!.getResource(fileName).readText()
            val result = source.toList<T>()
            result
        }.onFailure {
            println("cek failed: ${it.localizedMessage}")
        }.getOrDefault(emptyList())
    }

    @TypeConverter
    inline fun <reified T> String.toList(): List<T> {
        val type = Types.newParameterizedType(
            List::class.java,
            T::class.java
        )
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter = moshi.adapter<List<T>>(type)
        return adapter.fromJson(this).orEmpty()
    }
}
