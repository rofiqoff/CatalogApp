package id.test.catalogapp.presentation.detail

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import id.test.catalogapp.R
import id.test.catalogapp.domain.model.Product
import id.test.catalogapp.presentation.utils.UiState
import id.test.catalogapp.ui.component.AppTopBar

@Composable
fun ProductDetailScreen(
    navHostController: NavHostController,
    viewModel: ProductDetailViewModel = hiltViewModel(),
) {
    val product by viewModel.productData.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getProductDetail()
    }

    when (product) {
        is UiState.Success -> {
            val data = product.getDataContent()
            ProductDetailContent(data, onBackPressed = {
                navHostController.navigateUp()
            }, onFavoriteClick = {
                viewModel.saveAsFavorite(it)
            })
        }

        is UiState.Error -> {
            val message = product.getMessageError()
            if (message.isNotBlank()) Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        else -> {
            /* no-op */
        }
    }
}

@Composable
private fun ProductDetailContent(
    product: Product?,
    onBackPressed: () -> Unit = {},
    onFavoriteClick: (Boolean) -> Unit = {},
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = stringResource(id = R.string.label_detail),
                showBackPressed = true,
                onBackPressed = {
                    onBackPressed.invoke()
                })
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = product?.imageUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(2.dp)
                    .background(Color.Gray),
                contentScale = ContentScale.Crop,
                contentDescription = product?.name
            )

            Row {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .weight(1f),
                    text = product?.name.orEmpty(),
                    fontFamily = FontFamily.SansSerif,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis
                )

                val isFavorite = product?.isFavorite == true

                val favoriteIcon = if (isFavorite) {
                    Icons.Default.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                }

                Image(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .clickable {
                            onFavoriteClick.invoke(!isFavorite)
                        },
                    imageVector = favoriteIcon,
                    contentDescription = stringResource(
                        id = R.string.label_favorite
                    )
                )
            }

            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                text = product?.description.orEmpty(),
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun ProductDetailPreview() {
    val product = Product("", "Title", "Description", "")
    ProductDetailContent(product)
}

