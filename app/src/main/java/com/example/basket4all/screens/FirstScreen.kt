package com.example.basket4all.screens

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.basket4all.R
import com.example.basket4all.elements.IconButtonBottomBar
import com.example.basket4all.elements.NavigationBar
import com.example.basket4all.navigation.AppScreens
import com.example.basket4all.ui.theme.Basket4allTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FirstScreen (navController: NavController) {
    BodyContent(navController)
}

@Composable
private fun BodyContent(navController: NavController) {
    val navigationBar: NavigationBar? = NavigationBar.getInstance(navController)

    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Hello navigation")
        Button(onClick = {
            navController.navigate(route = AppScreens.SecondScreen.route + "/This is a parameter")
        }) {
            Text(text = "Navigate")
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        navigationBar?.NavBar()
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Preview () {
    Basket4allTheme {
        Scaffold(bottomBar = {
            BottomAppBar (
                modifier = Modifier.border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                ),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Row (modifier = Modifier
                    .fillMaxSize()
                    .align(Bottom)
                    .padding(top = 8.dp)
                ){
                    val buttonSize = 60.dp
                    val iconSize = 40.dp
                    val spacerSize = 20.dp
                    IconButtonBottomBar(
                        click = { /*TODO*/ },
                        buttonSize = buttonSize,
                        icon = R.drawable.account_circle,
                        iconSize = iconSize,
                        text = "Perfil",
                        description = "Icono de acceso al perfil"
                    )

                    Spacer(modifier = Modifier.width(spacerSize))
                    IconButtonBottomBar(
                        click = { /*TODO*/ },
                        buttonSize = buttonSize,
                        icon = R.drawable.calendar_month,
                        iconSize = iconSize,
                        text = "Eventos",
                        description = "Icono de acceso al calendario de eventos"
                    )

                    Spacer(modifier = Modifier.width(spacerSize))
                    IconButtonBottomBar(
                        click = { /*TODO*/ },
                        buttonSize = buttonSize,
                        icon = R.drawable.sports_basketball,
                        iconSize = iconSize,
                        text = "Equipo",
                        description = "Icono de acceso a la pantalla de equipo"
                    )

                    Spacer(modifier = Modifier.width(spacerSize))
                    IconButtonBottomBar(
                        click = { /*TODO*/ },
                        buttonSize = buttonSize,
                        icon = R.drawable.exercise,
                        iconSize = iconSize,
                        text = "Rutina",
                        description = "Icono de acceso a la ventana de ejercicios"
                    )

                    Spacer(modifier = Modifier.width(spacerSize))
                    IconButtonBottomBar(
                        click = { /*TODO*/ },
                        buttonSize = buttonSize,
                        icon = R.drawable.tactic,
                        iconSize = iconSize,
                        text = "Tácticas",
                        description = "Icono de acceso a las tácticas"
                    )
                }
            }
        }) {

        }
    }
}