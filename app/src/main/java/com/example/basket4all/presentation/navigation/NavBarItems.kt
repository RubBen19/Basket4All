package com.example.basket4all.presentation.navigation

import com.example.basket4all.R

sealed class NavBarItems(val iconID: Int, val name: String, val route: String) {
    object ProfileScreen: NavBarItems(
        iconID = R.drawable.account_circle,
        name = "Perfil",
        route = AppScreens.ProfileScreen.route
    )
    object CalendarScreen: NavBarItems(
        iconID = R.drawable.calendar_month,
        name = "Calendario",
        route = AppScreens.CalendarScreen.route
    )
    object TeamScreen: NavBarItems(
        iconID = R.drawable.sports_basketball,
        name = "Equipo",
        route = AppScreens.TeamScreen.route
    )
    object ExerciseScreen: NavBarItems(
        iconID = R.drawable.exercise,
        name = "Ejercicio",
        route = AppScreens.ExerciseScreen.route
    )
    object TacticsScreen: NavBarItems(
        iconID = R.drawable.tactic,
        name = "Táctica",
        route = AppScreens.TacticsScreen.route
    )
}
