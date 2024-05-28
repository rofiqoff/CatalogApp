package id.test.catalogapp.presentation.favorite

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
class FavoriteProductViewModel @Inject constructor(
    private val repository: ProductRepository,
) : ViewModel() {

    private val _productsData = MutableStateFlow<UiState<List<Product>>>(UiState.Idle)
    val productsData = _productsData.asStateFlow()

    fun getFavoriteProducts() {
        viewModelScope.launch {
            repository.getAllFavoriteProducts().collect { result ->
                _productsData.update { result.asUiState() }
            }
        }
    }

}
