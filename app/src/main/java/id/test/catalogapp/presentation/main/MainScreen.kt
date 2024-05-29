package id.test.catalogapp.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import id.test.catalogapp.presentation.main.bottomnavigation.BottomNavBarItem
import id.test.catalogapp.presentation.main.bottomnavigation.BottomNavigationBar

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    val items = listOf(
        BottomNavBarItem.Product,
        BottomNavBarItem.Favorite,
    )

    Scaffold(
        bottomBar = {
            val shouldShowBottomBar =
                navController.currentBackStackEntryAsState().value?.destination?.route in items.map { it.route }

            if (shouldShowBottomBar) BottomNavigationBar(
                navController = navController,
                items = items
            )
        }
    ) { padding ->
        MainHost(modifier = Modifier.padding(padding), navHostController = navController)
    }
}
