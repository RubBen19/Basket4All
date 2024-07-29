package com.example.basket4all.presentation.navigation

import com.example.basket4all.R

sealed class NavBarItems(val iconID: Int, val name: String, val route: String) {
    data object ProfileScreen: NavBarItems(
        iconID = R.drawable.account_circle,
        name = "Perfil",
        route = AppScreens.ProfileScreen.route
    )
    data object CalendarScreen: NavBarItems(
        iconID = R.drawable.calendar_month,
        name = "Calendario",
        route = AppScreens.CalendarScreen.route
    )
    data object TeamScreen: NavBarItems(
        iconID = R.drawable.sports_basketball,
        name = "Equipo",
        route = AppScreens.TeamScreen.route
    )
    data object ExerciseScreen: NavBarItems(
        iconID = R.drawable.emoji_events,
        name = "Club",
        route = AppScreens.ExerciseScreen.route
    )
    data object TacticsScreen: NavBarItems(
        iconID = R.drawable.tactic,
        name = "Táctica",
        route = AppScreens.TacticsScreen.route
    )
}
