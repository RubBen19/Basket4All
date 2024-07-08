package com.example.basket4all.presentation.uistate

data class LoginUiState(
    // Variables utilizadas en la pantalla de login
    val email: String = "",
    val password: String = "",
    val option: String = "",
    val hidden: Boolean = true,
    // Flag para permitir el login
    val login: Boolean = false,
    val loginError: Boolean = false
)
