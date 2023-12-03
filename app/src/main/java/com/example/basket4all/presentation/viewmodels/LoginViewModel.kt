package com.example.basket4all.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var email: String by mutableStateOf("")
    var password: String by mutableStateOf("")

    // Función para que un usuario inicie la sesión
    fun userLogin(): Boolean {
        return true
    }
}