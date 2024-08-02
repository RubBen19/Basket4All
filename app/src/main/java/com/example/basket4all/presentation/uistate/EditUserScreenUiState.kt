package com.example.basket4all.presentation.uistate

data class EditUserScreenUiState(
    val name: String = "",
    val surname: String = "",
    val surname2: String = "",
    val email: String = "",
    val number: String = "",
    val loading: Boolean = false
)