package id.test.catalogapp.ui.component.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import id.test.catalogapp.domain.model.Product

@Composable
fun ProductItem(
    product: Product,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {},
) {
    Card(
        modifier = modifier.clickable {
            onClick.invoke(product.id)
        }
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = product.imageUrl,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(2.dp)
                        .clip(RoundedCornerShape(4)),
                    contentScale = ContentScale.Crop,
                    contentDescription = product.name
                )

                val favoriteIcon = if (product.isFavorite)
                    Icons.Default.Favorite
                else
                    Icons.Default.FavoriteBorder

                Image(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    imageVector = favoriteIcon,
                    contentDescription = "Favorite"
                )
            }

            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                text = product.name,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black,
                fontSize = 16.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }


    }
}

@Preview(showBackground = true)
@Composable
private fun ProductItemPreview() {
    val product = Product("", "Title", "", "")
    ProductItem(product)
}
