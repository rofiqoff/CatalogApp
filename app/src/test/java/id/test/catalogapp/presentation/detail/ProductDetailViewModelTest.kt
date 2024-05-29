package id.test.catalogapp.presentation.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.turbineScope
import id.test.catalogapp.data.repositories.ProductRepositoryImpl
import id.test.catalogapp.data.source.local.dao.ProductDao
import id.test.catalogapp.data.source.local.entity.ProductEntity
import id.test.catalogapp.domain.utils.StringParser
import id.test.catalogapp.presentation.utils.UiState
import id.test.catalogapp.utils.MainDispatcherRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class ProductDetailViewModelTest {

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    private lateinit var productDao: ProductDao
    private lateinit var viewmodel: ProductDetailViewModel

    private val dataMock get() = StringParser.resourceToList<ProductEntity>("products.json")

    @Before
    fun setUp() {
        productDao = Mockito.mock()

        val savedStateHandle = SavedStateHandle()
        savedStateHandle["productId"] = "productId"

        val repository = ProductRepositoryImpl(productDao)
        viewmodel = ProductDetailViewModel(repository, savedStateHandle)
    }

    @Test
    fun givenProductData_whenGetProductDetail_shouldReturnSuccess() = runTest {
        turbineScope {
            // Given
            val productId = "productId"

            val result = listOf(dataMock.first().copy(productId = productId))
            `when`(productDao.getProduct(anyString())).thenReturn(result)

            val data = viewmodel.productData.testIn(backgroundScope)
            data.awaitItem()

            // When
            viewmodel.getProductDetail()

            // Then
            verify(productDao).getProduct(productId)

            val actualData = data.awaitItem()

            /* validate return state is success */
            val isSuccess = actualData is UiState.Success
            assertEquals(true, isSuccess)

            /* validate return data is correct */
            val dataProduct = (actualData as UiState.Success).data
            assertEquals(result.map { it.asProduct }.first(), dataProduct)
        }
    }

}
