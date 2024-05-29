package id.test.catalogapp.presentation.favorite

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import id.test.catalogapp.R
import id.test.catalogapp.domain.model.Product
import id.test.catalogapp.presentation.utils.UiState
import id.test.catalogapp.ui.component.AppTopBar
import id.test.catalogapp.ui.component.product.ProductListView

@Composable
fun FavoriteScreen(
    navHostController: NavHostController,
    viewModel: FavoriteProductViewModel = hiltViewModel(),
) {
    val productData by viewModel.productsData.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getFavoriteProducts()
    }

    when (productData) {
        is UiState.Success -> {
            val data = productData.getDataContent().orEmpty()
            FavoriteContent(data, onItemClick = {
                navHostController.navigate("detail/$it")
            })
        }

        is UiState.Error -> {
            val message = productData.getMessageError()
            if (message.isNotBlank()) Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        else -> {
            /* no-op */
        }
    }
}

@Composable
private fun FavoriteContent(
    dataProduct: List<Product>,
    onItemClick: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            AppTopBar(title = stringResource(id = R.string.label_favorite))
        }
    ) { paddingValue ->

        Box(modifier = Modifier.padding(paddingValue)) {
            ProductListView(
                products = dataProduct,
                onItemClick = { productId ->
                    onItemClick.invoke(productId)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoritePreview() {
    FavoriteContent(emptyList()) {}
}

