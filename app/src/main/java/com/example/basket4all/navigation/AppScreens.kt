package com.example.basket4all.navigation

sealed class AppScreens (val route: String) {
    object FirstScreen: AppScreens("first_screen")
    object SecondScreen: AppScreens("second_screen")
    object SplashScreen: AppScreens("splash_screen")
    object LogScreen: AppScreens("login_screen")
    object RegisterScreen: AppScreens("register_screen")
}
