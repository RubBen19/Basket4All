package com.example.basket4all.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun NewResultScreen() {
    var localScore by remember { mutableIntStateOf(0) }
    var visitorScore by remember { mutableIntStateOf(0) }

    var localTeam by remember { mutableStateOf<String?>(null) }
    var visitorTeam by remember { mutableStateOf<String?>(null) }

    var localTeamExpanded by remember { mutableStateOf(false) }
    var visitorTeamExpanded by remember { mutableStateOf(false) }

    val teams = listOf("TEAM A", "TEAM B", "TEAM C", "TEAM D")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de la pantalla
        Row {
            Text(
                text = "Nuevo ",
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "resultado",
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        // Boton de selección de equipo local
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedTextField(
                value = localScore.toString(),
                onValueChange = {
                    localScore = if (it.toIntOrNull() != null || it.isNotEmpty()) { it.toInt() }
                    else 0
                },
                textStyle = TextStyle(
                    fontSize = 60.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.width(140.dp),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { localTeamExpanded = true },
                colors = ButtonDefaults
                    .buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                (if (localTeam == null) "Selecciona equipo local" else localTeam)?.let {
                    Text(
                        text = it,
                        color = Color.White
                    )
                }
            }
            DropdownMenu(
                expanded = localTeamExpanded,
                onDismissRequest = { localTeamExpanded = false }
            ) {
                teams.forEach { team ->
                    DropdownMenuItem(
                        text = { Text(text = team) },
                        onClick = {
                            localTeam = team
                            localTeamExpanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        // Boton de selección de equipo visitante
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedTextField(
                value = visitorScore.toString(),
                onValueChange = {
                    visitorScore = if (it.toIntOrNull() != null || it.isNotEmpty()) { it.toInt() }
                    else 0
                },
                textStyle = TextStyle(
                    fontSize = 60.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.width(140.dp),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { visitorTeamExpanded = true },
                colors = ButtonDefaults
                    .buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                (if (visitorTeam == null) "Selecciona equipo visitante" else visitorTeam)?.let {
                    Text(
                        text = it,
                        color = Color.White
                    )
                }
            }
            DropdownMenu(
                expanded = visitorTeamExpanded,
                onDismissRequest = { visitorTeamExpanded = false }
            ) {
                teams.forEach { team ->
                    DropdownMenuItem(
                        text = { Text(text = team) },
                        onClick = {
                            visitorTeam = team
                            visitorTeamExpanded = false
                        }
                    )
                }
            }
        }
        Icon(
            imageVector = Icons.Default.AddCircle,
            contentDescription = "Añadir",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(80.dp)
                .padding(bottom = 12.dp)
        )
    }
}