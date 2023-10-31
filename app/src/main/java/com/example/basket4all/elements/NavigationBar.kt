package com.example.basket4all.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.basket4all.navigation.AppScreens
import com.example.basket4all.navigation.NavBarItems

/**
 * ARCHIVO: NavigationBar.kt
 * FUNCIÓN: El objetivo de este archivo es la funcionalidad de una barra de navegación
 */
class NavigationBar private constructor(private val navController: NavController) {

    private val barButtons = listOf<NavBarItems>(
        NavBarItems.ProfileScreen,
        NavBarItems.CalendarScreen,
        NavBarItems.TeamScreen,
        NavBarItems.ExerciseScreen,
        NavBarItems.TacticsScreen
    )

    companion object {
        private var instance: NavigationBar? = null

        fun getInstance(navController: NavController): NavigationBar? {
            if (instance == null) instance = NavigationBar(navController)
            return instance
        }
    }

    @Composable
    fun NavBar(modifier: Modifier = Modifier) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.onBackground,
            contentColor = MaterialTheme.colorScheme.background
        ) {

            val currentBackStackEntry by navController.currentBackStackEntryAsState()
            val actualScreenRoute: String? = currentBackStackEntry?.destination?.route

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                barButtons.forEach { screen ->
                    var selected by remember { mutableStateOf(false) }
                    selected = (actualScreenRoute == screen.route)
                    BarIcon(
                        iconID = screen.iconID,
                        text = screen.name,
                        current = selected
                    ) {
                        navController.navigate(screen.route) {
                            navController.popBackStack(
                                route = AppScreens.FirstScreen.route,
                                inclusive = false,
                                saveState = true
                            )
                        }
                    }
                }
            }

        }
    }

    @Composable
    private fun BarIcon(iconID: Int, text: String, current: Boolean, onClick: () -> Unit) {
        val imageSize = if (current) 36.dp else 32.dp
        Button(
            onClick = {
                onClick.invoke()
            },
            shape = RectangleShape,
            colors = buttonColors(
                containerColor = Color.Transparent,
                contentColor = if (current) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.background
            ),
            contentPadding = PaddingValues(12.dp)
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Imagen para el botón
                Icon(
                    painter = painterResource(id = iconID),
                    contentDescription = "Botón para ir a: $text",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(imageSize)
                        .padding(top = 2.dp)
                )
                // Texto para mostrar debajo de la imagen
                Text(
                    text = text,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 1.dp)
                )
            }
        }
    }

}