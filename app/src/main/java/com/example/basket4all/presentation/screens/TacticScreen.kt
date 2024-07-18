package com.example.basket4all.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.basket4all.common.messengers.SessionManager
import com.example.basket4all.presentation.navigation.AppScreens

@Composable
fun TacticsScreen(navController: NavHostController) {
    val showAlert = remember { mutableStateOf(false) }
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
            Buttons(showAlert, navController)
        }
        if (showAlert.value) {
            Alert(showAlert)
        }
    }
}

@Composable
private fun Alert(show: MutableState<Boolean>) {
    AlertDialog(
        onDismissRequest = { show.value = !show.value },
        confirmButton = { /*TODO*/ },
        shape = RoundedCornerShape(4),
        modifier = Modifier
            .border(3.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(4)),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Alerta",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(60.dp)
                )
            }
        },
        text = {
            Text(
                text = "No disponible para jugadores",
                color = MaterialTheme.colorScheme.background,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    )
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
            text = "táctica",
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
            text = "¿Preparado para subir de nivel?",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Sientate con tu bebida favorita y repasa a que juega tu equipo\n" +
                    "Y si eres entrenador saca tus estadísticas, " +
                    "ve al modo entrenador y nosotros nos encargamos de todo",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
private fun Buttons(showAlert: MutableState<Boolean>, navController: NavHostController) {
    val isPlayer = SessionManager.getInstance().getRole()
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {},
            shape = RectangleShape,
            modifier = Modifier
                .defaultMinSize(minWidth = 250.dp, minHeight = 50.dp)
        ) {
            Text(
                text = "Ver tácticas",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 24.sp
            )
        }
        Button(
            onClick = {
                //Si es un jugador mostrar la alerta
                if (isPlayer == true) {
                    showAlert.value = true
                }
                //Si no, navegar a la pantalla del modo entrenador
                else navController.navigate(AppScreens.CoachModeScreen.route)
            },
            shape = RectangleShape,
            modifier = Modifier
                .padding(top = 60.dp)
                .defaultMinSize(minWidth = 250.dp, minHeight = 50.dp)
        ) {
            Text(
                text = "Modo entrenador",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 24.sp
            )
        }
    }
}