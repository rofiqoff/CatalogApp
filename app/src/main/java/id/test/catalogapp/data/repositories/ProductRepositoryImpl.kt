package id.test.catalogapp.data.repositories

import id.test.catalogapp.data.source.local.dao.FavoriteProductDao
import id.test.catalogapp.data.source.local.dao.ProductDao
import id.test.catalogapp.data.source.local.entity.FavoriteProductEntity
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
    private val favoriteProductDao: FavoriteProductDao,
) : ProductRepository {

    override fun getAllProducts(): Flow<DataState<List<Product>>> = flow {
        val productLocalData = productDao.getAllProducts().asProductList()
        if (productLocalData.isEmpty()) {
            val productsDataFromSource = resourceToList<ProductEntity>("products.json").map {
                it.copy(productId = UUID.randomUUID().toString())
            }
            productDao.insertAllProduct(productsDataFromSource)

            emit(productsDataFromSource.asProductList())
        } else {
            emit(productLocalData)
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
        val favoritesProduct = favoriteProductDao.getAllFavoriteProducts()
        val productIds = favoritesProduct.map { it.productId }

        val products = productDao.getAllProductsByIds(productIds).asProductList()

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
