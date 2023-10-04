package com.example.basket4all.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.basket4all.navigation.AppScreens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreen(navController: NavController, text:String?) {
    Scaffold(topBar = {
        TopAppBar (title= { Text(text = "Second Screen", modifier = Modifier.padding(start = 8.dp))},
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
    ) {
        SecondBodyContent(navController, text)
    }
}

@Composable
private fun SecondBodyContent(navController: NavController, text: String?) {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hello, I'm the second screen!")
        text?.let { 
            Text(text = it)
        }
        Button(onClick = {
            navController.navigate(AppScreens.FirstScreen.route)
        }) {
            Text(text = "Return")
        }
    }
}