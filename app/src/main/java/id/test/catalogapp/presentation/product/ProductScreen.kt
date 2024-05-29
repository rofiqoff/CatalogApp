package id.test.catalogapp.presentation.product

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import id.test.catalogapp.R
import id.test.catalogapp.domain.model.Product
import id.test.catalogapp.presentation.utils.UiState
import id.test.catalogapp.ui.component.AppTopBar
import id.test.catalogapp.ui.component.SearchBarView
import id.test.catalogapp.ui.component.product.ProductListView

@Composable
fun ProductScreen(
    navHostController: NavHostController,
    productViewModel: ProductViewModel = hiltViewModel(),
) {
    val productData by productViewModel.productsData.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        productViewModel.getAllProducts()
    }

    when (productData) {
        is UiState.Success -> {
            val data = productData.getDataContent().orEmpty()
            ProductContent(
                dataProduct = data,
                onSearchChanges = {
                    productViewModel.searchProducts(it)
                },
                onItemClick = {
                    navHostController.navigate("detail/$it")
                }
            )
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
private fun ProductContent(
    dataProduct: List<Product>,
    onSearchChanges: (String) -> Unit,
    onItemClick: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            AppTopBar(title = stringResource(id = R.string.app_name))
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(paddingValue)
                .fillMaxSize()
        ) {
            SearchBarView(
                modifier = Modifier.padding(horizontal = 16.dp),
                onValueChange = {
                    onSearchChanges.invoke(it)
                }
            )

            ProductListView(products = dataProduct) { productId ->
                onItemClick.invoke(productId)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductPreview() {
    ProductContent(emptyList(), {}, {})
}

