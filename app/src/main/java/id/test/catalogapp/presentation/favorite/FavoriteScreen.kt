package id.test.catalogapp.presentation.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FavoriteScreen() {
    FavoriteContent()
}

@Composable
private fun FavoriteContent() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(modifier = Modifier.align(Alignment.Center), text = "Favorite")
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoritePreview() {
    FavoriteContent()
}

