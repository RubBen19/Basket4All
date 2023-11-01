package com.example.basket4all.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.basket4all.navigation.AppScreens
import com.example.basket4all.navigation.NavBarItems

/**
 * ARCHIVO: NavigationBar.kt
 * FUNCIÓN: El objetivo de este archivo es la funcionalidad de una barra de navegación
 */

// Obtengo todos los elementos que tendra la barra de navegación
private val barButtons = listOf<NavBarItems>(
    NavBarItems.ProfileScreen,
    NavBarItems.CalendarScreen,
    NavBarItems.TeamScreen,
    NavBarItems.ExerciseScreen,
    NavBarItems.TacticsScreen
)

// Método para implementar la barra de navegación
@Composable
fun B4AllNavigationBar(navController: NavController) {

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val actualScreenRoute: String? = currentBackStackEntry?.destination?.route

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        barButtons.forEach { item ->
            val selected = actualScreenRoute == item.route
            ButtonOfTheBar(screen = item, selected = selected) {
                navController.navigate(item.route) {
                    navController.popBackStack(
                        route = AppScreens.FirstScreen.route,
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