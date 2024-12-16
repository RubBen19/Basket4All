package com.example.basket4all.presentation.navigation

sealed class AppScreens (val route: String) {
    object HomeScreen: AppScreens("home_screen")
    object SecondScreen: AppScreens("second_screen")
    object SplashScreen: AppScreens("splash_screen")
    object LogScreen: AppScreens("login_screen")
    object RegisterScreen: AppScreens("register_screen")
    object ProfileScreen: AppScreens("profile_screen")
    object CalendarScreen: AppScreens("calendar_screen")
    object TeamScreen: AppScreens("team_screen")
    object ExerciseScreen: AppScreens("exercise_screen")
    object TacticsScreen: AppScreens("tactics_screen")
    object MatchesScreen: AppScreens("matches_screen")
    object TeamStatsScreen: AppScreens("team_stats_screen")
    object CoachModeScreen: AppScreens("coach_mode_screen")
    object NewMatchScreen: AppScreens("new_match_screen")
    object AddPlayerStatsScreen: AppScreens("add_new_player_stats")
    object BoardScreen: AppScreens("board_screen")
    object EditUserInfoScreen: AppScreens("edit_user_info")
    object MiLigueScreen: AppScreens("mi_ligue")
    object PlayerStatsScreen: AppScreens("player_stats")
}
