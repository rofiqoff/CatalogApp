package id.test.catalogapp.presentation.product

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import id.test.catalogapp.R
import id.test.catalogapp.domain.model.Product
import id.test.catalogapp.presentation.utils.UiState
import id.test.catalogapp.ui.component.EmptyView
import id.test.catalogapp.ui.component.SearchBarView
import id.test.catalogapp.ui.component.product.ProductListView

@Composable
fun ProductScreen(
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
            ProductContent(data, onSearchChanges = {
                productViewModel.searchProducts(it)
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
private fun ProductContent(
    dataProduct: List<Product>,
    onSearchChanges: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                fontSize = 20.sp,
                fontStyle = FontStyle.Normal,
                text = stringResource(id = R.string.app_name)
            )
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

            if (dataProduct.isNotEmpty()) {
                ProductListView(products = dataProduct) {

                }
            } else {
                EmptyView()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductPreview() {
    ProductContent(emptyList()) {}
}

