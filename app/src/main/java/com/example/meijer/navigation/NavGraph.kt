package com.example.meijer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.meijer.ui.screen.ProductDetailScreen
import com.example.meijer.ui.screen.ProductListScreen
import com.example.meijer.ui.viewmodel.ProductDetailViewModel
import com.example.meijer.ui.viewmodel.ProductListViewModel

/**
 * Navigation routes for the app.
 * Defines all possible destinations in the navigation graph.
 */
object Routes {
    const val PRODUCT_LIST = "product_list"
    const val PRODUCT_DETAIL = "product_detail/{productId}"
    
    /**
     * Helper function to build product detail route with product ID.
     */
    fun productDetail(productId: Int): String = "product_detail/$productId"
}

/**
 * Main navigation graph for the app.
 * Handles navigation between Product List and Product Detail screens.
 * 
 * @param navController NavHostController for managing navigation
 * @param productListViewModel ViewModel for the product list screen
 * @param productDetailViewModel ViewModel for the product detail screen
 */
@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    productListViewModel: ProductListViewModel,
    productDetailViewModel: ProductDetailViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Routes.PRODUCT_LIST
    ) {
        // Product List Screen
        composable(Routes.PRODUCT_LIST) {
            ProductListScreen(
                viewModel = productListViewModel,
                onProductClick = { productId ->
                    navController.navigate(Routes.productDetail(productId))
                }
            )
        }
        
        // Product Detail Screen
        composable(
            route = Routes.PRODUCT_DETAIL,
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            ProductDetailScreen(
                productId = productId,
                viewModel = productDetailViewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

