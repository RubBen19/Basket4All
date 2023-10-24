package com.example.basket4all.elements

import android.annotation.SuppressLint
import android.icu.text.ListFormatter.Width
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.basket4all.R
import com.example.basket4all.navigation.AppScreens

/**
 * ARCHIVO: NavigationBar.kt
 * FUNCIÓN: El objetivo de este archivo es la funcionalidad de una barra de navegación
 */
class NavigationBar private constructor(private var navController: NavController) {

    companion object {
        private var instance: NavigationBar? = null

        fun getInstance(navController: NavController): NavigationBar? {
            if (instance == null) instance = NavigationBar(navController)
            else instance!!.navController = navController
            return instance
        }
    }

    @Composable
    fun NavBar(modifier: Modifier = Modifier) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {

            BarIcon(
                iconID = R.drawable.account_circle,
                text = "Perfil",
                route = AppScreens.ProfileScreen.route
            )
            BarIcon(
                iconID = R.drawable.calendar_month,
                text = "Calendario",
                route = AppScreens.CalendarScreen.route
            )
            BarIcon(
                iconID = R.drawable.sports_basketball,
                text = "Equipo",
                route = AppScreens.TeamScreen.route
            )
            BarIcon(
                iconID = R.drawable.exercise,
                text = "Ejercicio",
                route = AppScreens.ExerciseScreen.route
            )
            BarIcon(
                iconID = R.drawable.tactic,
                text = "Táctica",
                route = AppScreens.TacticsScreen.route
            )
        }
    }

    @Composable
    private fun BarIcon(iconID: Int, text: String, route: String) {
        // Variable para saber si la app se encuentra en alguna de las Screen de la barra
        val current = this.navController.currentBackStackEntry?.destination?.route == route
        // Variable para indicar que tamaño tendrá el icono
        val imageSize = if (current) 40.dp else 36.dp

        Button(
            onClick = {
                navController.popBackStack(
                    route = AppScreens.FirstScreen.route,
                    inclusive = false,
                    saveState = true
                )
                navController.navigate(route)
            },
            shape = RectangleShape,
            contentPadding = PaddingValues(horizontal = 13.dp),
            colors = buttonColors(
                containerColor = MaterialTheme.colorScheme.onBackground,
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
                        .padding(top = 4.dp)
                )
                // Texto para mostrar debajo de la imagen

                Text(
                    text = text,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }

}