package com.example.basket4all.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen (navController: NavController) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "Crear nueva cuenta",
                modifier = Modifier.padding(start = 8.dp)
            )
        },
            navigationIcon = {
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Return",
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }
                )
            }
        )
    }
    ){
        BodyContent()
    }
}

@Composable
private fun BodyContent () {

}