package com.example.basket4all.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.basket4all.screens.FirstScreen
import com.example.basket4all.screens.LogScreen
import com.example.basket4all.screens.RegisterScreen
import com.example.basket4all.screens.SecondScreen
import com.example.basket4all.screens.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController= navController, startDestination = AppScreens.SplashScreen.route){
        composable(route = AppScreens.FirstScreen.route){
            FirstScreen(navController)
        }
        composable(route = AppScreens.SecondScreen.route + "/{text}",
            listOf(navArgument(name = "text"){
                type = NavType.StringType
            })
        ){
            SecondScreen(navController, it.arguments?.getString("text"))
        }
        composable(route = AppScreens.LogScreen.route){
            LogScreen(navController)
        }
        composable(route = AppScreens.SplashScreen.route){
            SplashScreen(navController)
        }
        composable(route = AppScreens.RegisterScreen.route) {
            RegisterScreen(navController)
        }
    }
}