package id.test.catalogapp.domain.repositories

import id.test.catalogapp.domain.model.Product
import id.test.catalogapp.domain.utils.DataState
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getAllProducts(): Flow<DataState<List<Product>>>

    fun getProductsByKeyword(key: String): Flow<DataState<List<Product>>>

    fun getProduct(productId: String): Flow<DataState<Product>>

    fun getAllFavoriteProducts(): Flow<DataState<List<Product>>>

    fun updateAsFavorite(productId: String, isFavorite: Boolean): Flow<DataState<Boolean>>
}
