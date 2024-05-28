package id.test.catalogapp.data.repositories

import id.test.catalogapp.domain.model.Product
import id.test.catalogapp.data.source.local.dao.FavoriteProductDao
import id.test.catalogapp.data.source.local.dao.ProductDao
import id.test.catalogapp.data.source.local.entity.FavoriteProductEntity
import id.test.catalogapp.domain.repositories.ProductRepository
import id.test.catalogapp.data.utils.DataState
import id.test.catalogapp.data.utils.asDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepositoryImpl(
    private val productDao: ProductDao,
    private val favoriteProductDao: FavoriteProductDao,
) : ProductRepository {

    override fun getAllProducts(): Flow<DataState<List<Product>>> = flow {
        val product = productDao.getAllProducts().map { it.asProduct }
        emit(product)
    }.asDataState()

    override fun getProductsByKeyword(key: String): Flow<DataState<List<Product>>> = flow {
        val results = productDao.findProductByKeyword(key).map { it.asProduct }
        emit(results)
    }.asDataState()

    override fun getProduct(productId: String): Flow<DataState<Product>> = flow {
        val results = productDao.getProduct(productId).asProduct
        emit(results)
    }.asDataState()

    override fun getAllFavoriteProducts(): Flow<DataState<List<Product>>> = flow {
        val favoritesProduct = favoriteProductDao.getAllFavoriteProducts()
        val productIds = favoritesProduct.map { it.productId }

        val products = productDao.getAllProductsByIds(productIds).map { it.asProduct }

        emit(products)
    }.asDataState()

    override fun insertToFavorite(productId: String): Flow<DataState<Boolean>> = flow {
        val data = FavoriteProductEntity(productId = productId)
        favoriteProductDao.insertToFavorite(data)
        emit(true)
    }.asDataState()

    override fun deleteProductFromFavorite(productId: String): Flow<DataState<Boolean>> = flow {
        favoriteProductDao.deleteFavorite(productId)
        emit(true)
    }.asDataState()
}
