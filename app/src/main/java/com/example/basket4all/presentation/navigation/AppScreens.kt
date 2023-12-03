package com.example.basket4all.presentation.navigation

sealed class AppScreens (val route: String) {
    object FirstScreen: AppScreens("first_screen")
    object SecondScreen: AppScreens("second_screen")
    object SplashScreen: AppScreens("splash_screen")
    object LogScreen: AppScreens("login_screen")
    object RegisterScreen: AppScreens("register_screen")
    object ProfileScreen: AppScreens("profile_screen")
    object CalendarScreen: AppScreens("calendar_screen")
    object TeamScreen: AppScreens("team_screen")
    object ExerciseScreen: AppScreens("exercise_screen")
    object TacticsScreen: AppScreens("tactics_screen")
}
