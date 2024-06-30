package com.example.basket4all.common.old_elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.basket4all.presentation.navigation.AppScreens
import com.example.basket4all.presentation.navigation.NavBarItems

/**
 * ARCHIVO: OLD_NavigationBar.kt
 * FUNCIÓN: El objetivo de este archivo es la funcionalidad de una barra de navegación
 */
private val barButtons = listOf<NavBarItems>(
    NavBarItems.ProfileScreen,
    NavBarItems.CalendarScreen,
    NavBarItems.TeamScreen,
    NavBarItems.ExerciseScreen,
    NavBarItems.TacticsScreen
)

@Composable
fun NavigationBar(navController: NavController, modifier: Modifier = Modifier) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.onBackground,
        contentColor = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {

        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val actualScreenRoute: String? = currentBackStackEntry?.destination?.route

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            var selected by remember { mutableStateOf(false) }
            barButtons.forEach { screen ->
                selected = (actualScreenRoute == screen.route)
                BarIcon(
                    iconID = screen.iconID,
                    text = screen.name,
                    current = selected
                ) {
                    navController.navigate(screen.route) {
                        navController.popBackStack(
                            route = AppScreens.HomeScreen.route,
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
    val imageSize = if (current) 28.dp else 24.dp
    Button(
        onClick = {
            onClick.invoke()
        },
        shape = RectangleShape,
        colors = buttonColors(
            containerColor = Color.Transparent,
            contentColor = if (current) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.background
        )
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
            )
            // Texto para mostrar debajo de la imagen
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = if (current) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.background
            )
        }
    }
}