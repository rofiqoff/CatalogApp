package id.test.catalogapp.data.repositories

import id.test.catalogapp.data.source.local.dao.ProductDao
import id.test.catalogapp.data.source.local.entity.ProductEntity
import id.test.catalogapp.data.source.local.entity.asProductList
import id.test.catalogapp.domain.model.Product
import id.test.catalogapp.domain.repositories.ProductRepository
import id.test.catalogapp.domain.utils.DataState
import id.test.catalogapp.domain.utils.StringParser.resourceToList
import id.test.catalogapp.domain.utils.asDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID

class ProductRepositoryImpl(
    private val productDao: ProductDao,
) : ProductRepository {

    override fun getAllProducts(): Flow<DataState<List<Product>>> = flow {
        val localData = productDao.getAllProducts().asProductList()

        if (localData.isEmpty()) {
            val productsDataFromSource = resourceToList<ProductEntity>("products.json").map {
                it.copy(productId = UUID.randomUUID().toString())
            }
            productDao.insertAllProduct(productsDataFromSource)

            val productLocalData = productDao.getAllProducts().asProductList()
            emit(productLocalData)
        } else {
            emit(localData)
        }

    }.asDataState()

    override fun getProductsByKeyword(key: String): Flow<DataState<List<Product>>> = flow {
        val results = productDao.findProductByKeyword(key).asProductList()
        emit(results)
    }.asDataState()

    override fun getProduct(productId: String): Flow<DataState<Product>> = flow {
        val results = productDao.getProduct(productId).asProduct
        emit(results)
    }.asDataState()

    override fun getAllFavoriteProducts(): Flow<DataState<List<Product>>> = flow {
        val favoritesProduct = productDao.getAllProducts().filter { it.isFavorite }.asProductList()
        emit(favoritesProduct)
    }.asDataState()

    override fun updateAsFavorite(productId: String, isFavorite: Boolean) = flow {
        productDao.updateIsFavoriteProduct(productId, isFavorite)
        emit(true)
    }.asDataState()
}
