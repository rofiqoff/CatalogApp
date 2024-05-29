package id.test.catalogapp.presentation.main.bottomnavigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import id.test.catalogapp.R

sealed class BottomNavBarItem(
    val route: String,
    @StringRes val label: Int,
    val icon: ImageVector,
) {

    data object Product : BottomNavBarItem(
        route = "product",
        label = R.string.label_product,
        icon = Icons.Default.Home
    )

    data object Favorite : BottomNavBarItem(
        route = "favorite",
        label = R.string.label_favorite,
        icon = Icons.Default.Favorite
    )

}
