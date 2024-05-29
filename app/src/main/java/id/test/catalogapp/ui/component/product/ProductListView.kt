package id.test.catalogapp.ui.component.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.test.catalogapp.domain.model.Product

@Composable
fun ProductListView(
    products: List<Product>,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit = {},
) {
    LazyVerticalGrid(
        modifier = modifier.padding(16.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(products) {
            ProductItem(it) { productId ->
                onItemClick.invoke(productId)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductListPreview() {
    val listProduct = listOf(Product("", "title", "", ""))
    ProductListView(listProduct)
}
