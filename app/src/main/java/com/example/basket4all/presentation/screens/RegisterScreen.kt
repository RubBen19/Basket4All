package com.example.basket4all.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.basket4all.R
import com.example.basket4all.common.enums.CoachRoles
import com.example.basket4all.common.enums.PlayerPositions
import com.example.basket4all.common.old_elements.TextButtonMain
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ARCHIVO: RegisterScreen.kt
 * FUNCIÓN: El objetivo de este archivo es la funcionalidad de la pantalla de registro
 *          para nuevos usuarios
 */

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen (navHostController: NavHostController) {
    BodyContent()
}

@Composable
private fun BodyContent () {
    Box(modifier = Modifier.fillMaxSize()){
        Formulary()
    }
}

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Formulary () {
    // User values
    var name by remember { mutableStateOf("") }
    var surname1 by remember { mutableStateOf("") }
    var surname2 by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthdate by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var hidden by remember { mutableStateOf(true) }
    var playerAccount by remember { mutableStateOf(true) }
    var team by remember { mutableStateOf("") }
    // Player values
    val positions by remember { mutableStateOf(mutableListOf<String>()) }
    var number by remember { mutableStateOf("") }
    // Coach values
    val roles by remember { mutableStateOf(mutableListOf<String>()) }

    // Flags
    var positionsExpanded by remember { mutableStateOf(false) }
    var rolesExpanded by remember { mutableStateOf(false) }

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
            OutlinedTextField(
                value = name,
                onValueChange = {name = it},
                label = { Text(text = "Nombre") },
                singleLine = true,
                modifier = Modifier
                    .size(height = 60.dp, width = 250.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = surname1,
                onValueChange = {surname1 = it},
                label = { Text(text = "Primer apellido") },
                singleLine = true,
                modifier = Modifier
                    .size(height = 60.dp, width = 250.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = surname2,
                onValueChange = {surname2 = it},
                label = { Text(text = "Segundo apellido") },
                singleLine = true,
                modifier = Modifier
                    .size(height = 60.dp, width = 250.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                label = { Text(text = "E-Mail") },
                singleLine = true,
                modifier = Modifier
                    .size(height = 60.dp, width = 250.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = birthdate,
                onValueChange = {birthdate = it},
                label = { Text(text = "Fecha de nacimiento") },
                singleLine = true,
                modifier = Modifier
                    .size(height = 60.dp, width = 250.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = {password = it},
                label = { Text(text = "Contraseña") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                visualTransformation = if(hidden) PasswordVisualTransformation ()
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
            if (playerAccount) {
                OutlinedTextField(
                    value = number,
                    onValueChange = { number = it },
                    label = { Text("Dorsal") },
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
        item {
            TextButtonMain(
                text = "Crear cuenta",
                click = {/*TODO*/ },
                Alignment.CenterHorizontally
            )
        }

    }
}