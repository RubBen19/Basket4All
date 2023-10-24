package com.example.basket4all.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.basket4all.FirebaseAuthService
import com.example.basket4all.R
import com.example.basket4all.elements.TextButtonMain
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
fun RegisterScreen (navHostController: NavHostController, authService: FirebaseAuthService) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "¿Preparado para jugar?",
                modifier = Modifier.padding(start = 10.dp, end = 8.dp)
            )
        },
            navigationIcon = {
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Return",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clickable {
                            navHostController.popBackStack()
                        }
                        .size(30.dp)
                )
            }
        )
    }
    ){
        BodyContent(authService)
    }
}

@Composable
private fun BodyContent (authService: FirebaseAuthService) {
    Box(modifier = Modifier.fillMaxSize()){
        Formulary(authService)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Formulary (authService: FirebaseAuthService) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var hidden by remember { mutableStateOf(true) }

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = { Text(text = "E-Mail") },
            singleLine = true,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
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
                .align(Alignment.CenterHorizontally)
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
        Spacer(modifier = Modifier.height(18.dp))
        TextButtonMain(
            text = "Crear cuenta",
            click = {
                if (email != "" && password != "") {
                    CoroutineScope(Dispatchers.IO).launch {
                        authService.createAccount(email, password)
                    }
                }
                else {
                    /*TODO*/
                }
            },
            Alignment.CenterHorizontally)
    }
}