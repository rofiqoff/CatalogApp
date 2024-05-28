package id.test.catalogapp.presentation.product

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
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class ProductViewModelTest {

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    private lateinit var productDao: ProductDao

    private lateinit var viewmodel: ProductViewModel

    private val dataMock get() = StringParser.resourceToList<ProductEntity>("products.json")

    @Before
    fun setUp() {
        productDao = mock()

        val repository = ProductRepositoryImpl(productDao)
        viewmodel = ProductViewModel(repository)
    }

    @Test
    fun givenDataList_whenGetAllProductsDataFromDatabase_shouldReturnSuccessWithDataList() =
        runTest {
            turbineScope {
                // Given
                `when`(productDao.getAllProducts()).thenReturn(dataMock)

                val data = viewmodel.productsData.testIn(backgroundScope)
                data.awaitItem()

                // When
                viewmodel.getAllProducts()

                // Then
                verify(productDao).getAllProducts()

                val actualData = data.awaitItem()

                /* validate return state is success */
                val isSuccess = actualData is UiState.Success
                assertEquals(true, isSuccess)

                /* validate return data entity is not empty */
                val dataSize = (actualData as UiState.Success).data.size
                assertEquals(dataMock.size, dataSize)
                assertEquals(dataMock.asProductList(), actualData.data)
            }
        }

    @Test
    fun givenEmptyDataList_whenGetAllProductsDataFromDatabase_shouldReturnSuccessWithDataList() =
        runTest {
            turbineScope {
                // Given
                `when`(productDao.getAllProducts()).thenReturn(emptyList())

                val data = viewmodel.productsData.testIn(backgroundScope)
                data.awaitItem()

                // When
                viewmodel.getAllProducts()

                // Then
                val actualData = data.awaitItem()

                /* check return state is success */
                val isSuccess = actualData is UiState.Success
                assertEquals(true, isSuccess)

                /* check return data entity */
                val dataSize = (actualData as UiState.Success).data.size
                assertEquals(3, dataSize)
            }
        }

    @Test
    fun givenDataList_whenSearchProductWithKeyword_shouldReturnSuccessWithDataList() = runTest {
        turbineScope {
            val keyWord = "name"

            // Given
            val result = dataMock.filter { it.name.contains(keyWord, true) }
            `when`(productDao.findProductByKeyword(anyString())).thenReturn(result)

            val data = viewmodel.productsData.testIn(backgroundScope)
            data.awaitItem()

            // When
            viewmodel.searchProducts(keyWord)

            // Then
            verify(productDao).findProductByKeyword(keyWord)

            val actualData = data.awaitItem()

            /* check return state is success */
            val isSuccess = actualData is UiState.Success
            assertEquals(true, isSuccess)

            /* check return data entity */
            val dataSize = (actualData as UiState.Success).data.size
            assertEquals(result.size, dataSize)
            assertEquals(result.asProductList(), actualData.data)
        }
    }

    @Test
    fun givenDataList_whenSearchProductWithEmptyKeyword_shouldReturnSuccessWithDataList() =
        runTest {
            turbineScope {
                val keyWord = ""

                // Given
                `when`(productDao.getAllProducts()).thenReturn(dataMock)

                val data = viewmodel.productsData.testIn(backgroundScope)
                data.awaitItem()

                // When
                viewmodel.searchProducts(keyWord)

                // Then
                verify(productDao).getAllProducts()

                val actualData = data.awaitItem()

                /* check return state is success */
                val isSuccess = actualData is UiState.Success
                assertEquals(true, isSuccess)

                /* check return data entity */
                val dataSize = (actualData as UiState.Success).data.size
                assertEquals(dataMock.size, dataSize)
                assertEquals(dataMock.asProductList(), actualData.data)
            }
        }

}
