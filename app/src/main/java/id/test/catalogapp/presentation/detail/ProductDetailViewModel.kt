package id.test.catalogapp.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.test.catalogapp.domain.model.Product
import id.test.catalogapp.domain.repositories.ProductRepository
import id.test.catalogapp.presentation.utils.UiState
import id.test.catalogapp.presentation.utils.asUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val repository: ProductRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val productId: String = checkNotNull(savedStateHandle["productId"])

    private val _productData = MutableStateFlow<UiState<Product>>(UiState.Idle)
    val productData = _productData.asStateFlow()

    fun getProductDetail() {
        viewModelScope.launch {
            repository.getProduct(productId).collect { result ->
                _productData.update { result.asUiState() }
            }
        }
    }

    fun saveAsFavorite(isFavorite: Boolean) {
        viewModelScope.launch {
            repository.updateAsFavorite(productId, isFavorite).collect { result ->
                if (result.asUiState().isSuccess()) getProductDetail()
            }
        }
    }
}
