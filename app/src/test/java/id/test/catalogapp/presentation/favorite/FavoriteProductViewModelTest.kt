package id.test.catalogapp.presentation.favorite

import app.cash.turbine.turbineScope
import id.test.catalogapp.data.repositories.ProductRepositoryImpl
import id.test.catalogapp.data.source.local.dao.ProductDao
import id.test.catalogapp.data.source.local.entity.ProductEntity
import id.test.catalogapp.data.source.local.entity.asProductList
import id.test.catalogapp.domain.utils.StringParser
import id.test.catalogapp.presentation.utils.UiState
import id.test.catalogapp.utils.MainDispatcherRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class FavoriteProductViewModelTest {

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    private lateinit var productDao: ProductDao
    private lateinit var viewmodel: FavoriteProductViewModel

    private val dataMock get() = StringParser.resourceToList<ProductEntity>("products.json")

    @Before
    fun setUp() {
        productDao = mock()

        val repository = ProductRepositoryImpl(productDao)
        viewmodel = FavoriteProductViewModel(repository)
    }

    @Test
    fun givenDataListWithFavoriteTrue_whenGetAllFavoriteProductList_shouldReturnSuccess() =
        runTest {
            turbineScope {
                // Given
                val result = dataMock.map { it.copy(isFavorite = true) }
                `when`(productDao.getAllProducts()).thenReturn(result)

                val data = viewmodel.productsData.testIn(backgroundScope)
                data.awaitItem()

                // When
                viewmodel.getFavoriteProducts()

                // Then
                verify(productDao).getAllProducts()

                val actualData = data.awaitItem()

                /* validate return state is success */
                val isSuccess = actualData is UiState.Success
                assertEquals(true, isSuccess)

                /* validate return data entity is not empty */
                val dataSize = (actualData as UiState.Success).data.size
                assertEquals(result.size, dataSize)
                assertEquals(result.asProductList(), actualData.data)
            }
        }

    @Test
    fun givenDataListWithFavoriteFalse_whenGetAllFavoriteProductList_shouldReturnSuccess() =
        runTest {
            turbineScope {
                // Given
                val result = dataMock.map { it.copy(isFavorite = false) }
                `when`(productDao.getAllProducts()).thenReturn(result)

                val data = viewmodel.productsData.testIn(backgroundScope)
                data.awaitItem()

                // When
                viewmodel.getFavoriteProducts()

                // Then
                verify(productDao).getAllProducts()

                val actualData = data.awaitItem()

                /* validate return state is success */
                val isSuccess = actualData is UiState.Success
                assertEquals(true, isSuccess)

                /* validate return data entity is empty */
                val dataSize = (actualData as UiState.Success).data.size
                assertEquals(0, dataSize)
            }
        }

}
