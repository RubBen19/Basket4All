package com.example.basket4all.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.basket4all.elements.NavigationBar

@Composable
fun TacticsScreen(navController: NavController) {
    val navigationBar: NavigationBar? = NavigationBar.getInstance(navController)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        navigationBar?.NavBar()
    }
}