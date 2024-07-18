package com.example.basket4all.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.basket4all.common.messengers.NewMatchCourier
import com.example.basket4all.presentation.navigation.AppScreens

@Composable
fun CoachModeScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título de la screen
            Title()
            // Descripción
            Description()
            // Botones
            Buttons(navController)
        }
    }
}


@Composable
private fun Title() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 36.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Modo ",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 40.sp
        )
        Text(
            text = "entrenador",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 40.sp
        )
    }
}

@Composable
private fun Description() {
    Column(
        modifier = Modifier.padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "¿Que puedes hacer?",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = "El modo entrenador te permite crear y editar las tácticas de tu equipo.\n" +
                    "Además de añadir los partidos con las estadísticas de tus jugadores\n" +
                    "Y llevar tu pizarra de entrenador contigo",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
private fun Buttons(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { navController.navigate(AppScreens.BoardScreen.route) },
            shape = RectangleShape,
            modifier = Modifier
                .defaultMinSize(minWidth = 250.dp, minHeight = 50.dp)
        ) {
            Text(
                text = "Modo pizarra",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 24.sp
            )
        }
        Button(
            onClick = { /*TODO*/ },
            shape = RectangleShape,
            modifier = Modifier
                .defaultMinSize(minWidth = 250.dp, minHeight = 50.dp)
                .padding(top = 40.dp)
        ) {
            Text(
                text = "Crear tácticas",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 24.sp
            )
        }
        Button(
            onClick = { /*TODO*/ },
            shape = RectangleShape,
            modifier = Modifier
                .defaultMinSize(minWidth = 250.dp, minHeight = 50.dp)
                .padding(top = 40.dp)
        ) {
            Text(
                text = "Editar tácticas",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 24.sp
            )
        }
        Button(
            onClick = {
                navController.navigate(AppScreens.NewMatchScreen.route)
                NewMatchCourier.getInstance().newMatch()
            },
            shape = RectangleShape,
            modifier = Modifier
                .padding(top = 40.dp)
                .defaultMinSize(minWidth = 250.dp, minHeight = 50.dp)
        ) {
            Text(
                text = "Nuevo partido",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 24.sp
            )
        }
    }
}