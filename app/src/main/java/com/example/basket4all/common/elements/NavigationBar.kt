package com.example.basket4all.common.elements

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.basket4all.common.messengers.SessionManager
import com.example.basket4all.presentation.navigation.AppScreens
import com.example.basket4all.presentation.navigation.NavBarItems

/**
 * ARCHIVO: NavigationBar.kt
 * FUNCIÓN: El objetivo de este archivo es la funcionalidad de una barra de navegación
 */

// Método para implementar la barra de navegación
@Composable
fun B4AllNavigationBar(navController: NavController) {

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val actualScreenRoute: String? = currentBackStackEntry?.destination?.route
    val sessionManager: SessionManager = SessionManager.getInstance()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        if (actualScreenRoute != null) {
            val profileScreen = NavBarItems.ProfileScreen
            val routeOfMyProfile = profileScreen.route + "/${sessionManager.getUserId()}/${sessionManager.getRole()}"
            ButtonOfTheBar(screen = profileScreen, selected = actualScreenRoute.contains(profileScreen.route)) {
                navController.navigate(routeOfMyProfile) {
                    navController.popBackStack(
                        route = AppScreens.HomeScreen.route,
                        inclusive = false,
                        saveState = false
                    )
                }
            }

            val calendarScreen = NavBarItems.CalendarScreen
            ButtonOfTheBar(screen = calendarScreen, selected = actualScreenRoute == calendarScreen.route) {
                navController.navigate(calendarScreen.route) {
                    navController.popBackStack(
                        route = AppScreens.HomeScreen.route,
                        inclusive = false,
                        saveState = false
                    )
                }
            }

            val teamScreen = NavBarItems.TeamScreen
            val routeOfMyTeam = teamScreen.route + "/${sessionManager.getTeamId()}"
            ButtonOfTheBar(screen = teamScreen, selected = actualScreenRoute.contains(teamScreen.route)) {
                Log.d("NavBar", routeOfMyTeam)
                navController.navigate(routeOfMyTeam) {
                    navController.popBackStack(
                        route = AppScreens.HomeScreen.route,
                        inclusive = false,
                        saveState = false
                    )
                }
            }

            val exerciseScreen = NavBarItems.ExerciseScreen
            ButtonOfTheBar(screen = exerciseScreen, selected = actualScreenRoute == exerciseScreen.route) {
                navController.navigate(exerciseScreen.route) {
                    navController.popBackStack(
                        route = AppScreens.HomeScreen.route,
                        inclusive = false,
                        saveState = false
                    )
                }
            }

            val tacticScreen = NavBarItems.TacticsScreen
            ButtonOfTheBar(screen = tacticScreen, selected = actualScreenRoute == tacticScreen.route) {
                navController.navigate(tacticScreen.route) {
                    navController.popBackStack(
                        route = AppScreens.HomeScreen.route,
                        inclusive = false,
                        saveState = false
                    )
                }
            }
        }
    }
}

@Composable
private fun ButtonOfTheBar(screen: NavBarItems, selected: Boolean, clickable: ()->Unit) {
    val imageSize: Int = if (selected) 36 else 32
    val color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
    Column(
        modifier = Modifier
            .clickable { clickable.invoke() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Icon(
            painter = painterResource(id = screen.iconID),
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(imageSize.dp)
        )
        Text(
            text = screen.name,
            color = color,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}