package com.example.wishlistapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wishlistapp.AddDetailView
import com.example.wishlistapp.HomeView
import com.example.wishlistapp.WishViewModel

@Composable
fun Navigator(
    navController: NavHostController = rememberNavController(),
    viewModel: WishViewModel = viewModel()
) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(Screen.HomeScreen.route) {
            HomeView(navController, viewModel)
        }
        
        composable(Screen.AddScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                }
            )
        ) {
            entry ->
            val id = if(entry.arguments != null) entry.arguments!!.getLong("id") else 0L
            AddDetailView(id = id, viewModel = viewModel, navController = navController)
        }
    }
}