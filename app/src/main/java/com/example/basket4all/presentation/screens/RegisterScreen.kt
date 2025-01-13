package com.example.basket4all.presentation.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.basket4all.R
import com.example.basket4all.common.enums.CoachRoles
import com.example.basket4all.common.enums.PlayerPositions
import com.example.basket4all.presentation.viewmodels.db.CoachesViewModel
import com.example.basket4all.presentation.viewmodels.db.PlayersViewModel
import com.example.basket4all.presentation.viewmodels.screens.RegisterScreenViewModel
import com.example.basket4all.presentation.viewmodels.screens.RegisterScreenViewModelFactory
import java.time.LocalDate
import java.util.Calendar

/**
 * ARCHIVO: RegisterScreen.kt
 * FUNCIÓN: El objetivo de este archivo es la funcionalidad de la pantalla de registro
 *          para nuevos usuarios
 */

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterScreen (playersViewModel: PlayersViewModel, coachesViewModel: CoachesViewModel) {
    // Declaración del viewmodel y estado
    val registerScreenViewModel: RegisterScreenViewModel = viewModel(
        factory = RegisterScreenViewModelFactory(playersViewModel, coachesViewModel)
    )

    Box(modifier = Modifier.fillMaxSize()){
        Formulary(registerScreenViewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
private fun Formulary (viewModel: RegisterScreenViewModel) {
    // User values
    var name by remember { mutableStateOf("") }
    var surname1 by remember { mutableStateOf("") }
    var surname2 by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthdate by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var hidden by remember { mutableStateOf(true) }
    var playerAccount by remember { mutableStateOf(true) }
    var teamCode by remember { mutableStateOf("0000") }
    // Player values
    val positions by remember { mutableStateOf(mutableListOf<String>()) }
    var number by remember { mutableStateOf("") }
    // Coach values
    val roles by remember { mutableStateOf(mutableListOf<String>()) }
    // Flags
    var positionsExpanded by remember { mutableStateOf(false) }
    var rolesExpanded by remember { mutableStateOf(false) }
    // Context
    val context = LocalContext.current.applicationContext
    // Ui state
    val screenUiState by viewModel.uiState.collectAsState()
    var roleReplacement = false

    LazyColumn (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text(
                text = "Crear una nueva cuenta",
                fontSize = 28.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        // Name label
        item {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Nombre") },
                singleLine = true,
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                modifier = Modifier
                    .size(height = 60.dp, width = 250.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        // First surname label
        item {
            OutlinedTextField(
                value = surname1,
                onValueChange = { surname1 = it },
                label = { Text(text = "Primer apellido") },
                singleLine = true,
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                modifier = Modifier
                    .size(height = 60.dp, width = 250.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        // Second surname label
        item {
            OutlinedTextField(
                value = surname2,
                onValueChange = { surname2 = it },
                label = { Text(text = "Segundo apellido") },
                singleLine = true,
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                modifier = Modifier
                    .size(height = 60.dp, width = 250.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        // Email label
        item {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "E-Mail") },
                singleLine = true,
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                modifier = Modifier
                    .size(height = 60.dp, width = 250.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        // Birthdate selector
        item {
            birthdate = dateField()
            Spacer(modifier = Modifier.height(8.dp))
        }
        // Password label
        item {
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Contraseña") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                visualTransformation = if (hidden) PasswordVisualTransformation()
                else VisualTransformation.None,
                modifier = Modifier
                    .size(height = 60.dp, width = 250.dp),
                trailingIcon = {
                    IconButton(onClick = { hidden = !hidden }) {
                        Icon(
                            painterResource(
                                id = if (hidden) R.drawable.visibility_off
                                else R.drawable.visibility_on
                            ),
                            contentDescription = "Visibility",
                            tint = if (hidden) Color.Gray
                            else MaterialTheme.colorScheme.primary
                        )
                    }
                },
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        // Account type switch selector
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Soy entrenador",
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Switch(checked = playerAccount, onCheckedChange = { playerAccount=it })
                Text(
                    text = "Soy jugador",
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Positions selector list
            if (playerAccount) {
                OutlinedTextField(
                    value = number,
                    onValueChange = { number = it },
                    label = { Text("Dorsal") },
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                    modifier = Modifier
                        .size(height = 60.dp, width = 250.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Box {
                    Button(
                        onClick = { positionsExpanded = true },
                        modifier = Modifier
                            .size(height = 60.dp, width = 240.dp)
                            .padding(vertical = 12.dp),
                    ) {
                        Text(
                            text = "¿Donde puedes jugar?",
                            color = Color.White
                        )
                    }
                    DropdownMenu(
                        expanded = positionsExpanded,
                        onDismissRequest = { positionsExpanded = false }
                    ) {
                        PlayerPositions.entries.forEach { position ->
                            DropdownMenuItem(
                                text = { Text(position.name) },
                                onClick = {
                                    if (positions.contains(position.name))
                                        positions.remove(position.name)
                                    else positions.add(position.name)
                                    positionsExpanded = false
                                    positionsExpanded = true
                                },
                                leadingIcon = {
                                    if (positions.contains(position.name))
                                        Icon(
                                            Icons.Default.Clear,
                                            contentDescription = "Quitar"
                                        )
                                    else
                                        Icon(
                                            Icons.Default.AddCircle,
                                            contentDescription = "Añadir"
                                        )
                                }
                            )
                        }
                    }
                }
            }
            // Roles selector list
            else {
                Box {
                    Button(
                        onClick = { rolesExpanded = true },
                        modifier = Modifier
                            .size(height = 60.dp, width = 240.dp)
                            .padding(vertical = 12.dp),
                    ) {
                        Text(
                            text = "Selecciona tus roles",
                            color = Color.White
                        )
                    }
                    DropdownMenu(
                        expanded = rolesExpanded,
                        onDismissRequest = { rolesExpanded = false }
                    ) {
                        CoachRoles.entries.forEach { role ->
                            DropdownMenuItem(
                                text = { Text(role.name) },
                                onClick = {
                                    if (roles.contains(role.name))
                                        roles.remove(role.name)
                                    else roles.add(role.name)
                                    rolesExpanded = false
                                    rolesExpanded = true
                                },
                                trailingIcon = {
                                    if (roles.contains(role.name))
                                        Icon(
                                            Icons.Default.Clear,
                                            contentDescription = "Quitar"
                                        )
                                    else
                                        Icon(
                                            Icons.Default.AddCircle,
                                            contentDescription = "Añadir"
                                        )
                                }
                            )
                        }
                    }
                }
            }
        }
        // Team code label
        item {
            OutlinedTextField(
                value = teamCode,
                onValueChange = {teamCode = it},
                label = { Text(text = "Código de equipo") },
                singleLine = true,
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .size(height = 60.dp, width = 250.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        // Create new account button
        item {
            Button(
                onClick = {
                    if (playerAccount) {
                        if (number.isEmpty()) number = "0"
                        viewModel
                            .registerNewPlayer(
                                name, surname1, surname2, email, birthdate, password, teamCode,
                                positions, number.toInt(), context
                            )
                    }
                    else {
                        if (roles.contains("MAIN")) {
                            roleReplacement = true
                            roles.remove("MAIN")
                            if (!roles.contains("SECOND")) roles.add("SECOND")
                        }
                        viewModel
                            .registerNewCoach(
                                name, surname1, surname2, email, birthdate, password, teamCode,
                                roles, context
                            )
                    }
                    viewModel.getErrorMessage()
                }
            ) {
                Text(
                    text = "Crear cuenta",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
        // Error or confirm message
        item {
            if (!screenUiState.showConfirmMsg)
                Text(
                    text = screenUiState.msg,
                    color = MaterialTheme.colorScheme.error
                )
            else
                Column {
                    Text(
                        text = "Cuenta creada exitosamente, revisa tu correo",
                        color = Color.Green
                    )
                    if (roleReplacement) {
                        Text(
                            text = "Como ya existe un primer entrenador tu rol ha sido modificado",
                            color = Color.Green
                        )
                    }
                }

        }
    }
}

// Funcion que invoca un selector de fecha
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun dateField(): String {
    var selectedDate by remember { mutableStateOf("") }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    var date = ""

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        },
        year,
        month,
        day
    )

    OutlinedTextField(
        value = selectedDate,
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
        onValueChange = {},
        modifier = Modifier
            .size(height = 60.dp, width = 250.dp)
            .clickable { datePickerDialog.show() },
        label = { Text("Fecha de nacimiento") },
        readOnly = true,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Introduce tu fecha de nacimiento",
                modifier = Modifier.clickable { datePickerDialog.show() }
            )
        }
    )
    return selectedDate
}