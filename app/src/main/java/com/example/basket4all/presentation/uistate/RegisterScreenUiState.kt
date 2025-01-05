package com.example.basket4all.presentation.uistate

data class RegisterScreenUiState(
    // Flags
    val hidden: Boolean = true,
    val playerAccount: Boolean = true,
    val positionsExpanded: Boolean = false,
    val rolesExpanded: Boolean = false,
    val showConfirmMsg: Boolean = false,
    // Errors flags
    val emailFormatError: Boolean = false,
    val nameFormatError: Boolean = false,
    val surnameFormatError: Boolean = false,
    val passwordFormatError: Boolean = false,
    val birthdateFormatError: Boolean = false,
    val teamCodeFormatError: Boolean = false
)
