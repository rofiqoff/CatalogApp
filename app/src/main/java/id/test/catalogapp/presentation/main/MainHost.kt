package id.test.catalogapp.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import id.test.catalogapp.presentation.favorite.FavoriteScreen
import id.test.catalogapp.presentation.main.bottomnavigation.BottomNavBarItem
import id.test.catalogapp.presentation.product.ProductScreen

@Composable
fun MainHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    startDestination: String = BottomNavBarItem.Product.route,
) {

    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = startDestination
    ) {

        composable(BottomNavBarItem.Product.route) {
            ProductScreen()
        }

        composable(BottomNavBarItem.Favorite.route) {
            FavoriteScreen()
        }

    }

}
