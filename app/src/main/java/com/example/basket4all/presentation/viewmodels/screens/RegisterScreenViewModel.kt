package com.example.basket4all.presentation.viewmodels.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.basket4all.presentation.uistate.RegisterScreenUiState
import com.example.basket4all.presentation.viewmodels.db.CoachesViewModel
import com.example.basket4all.presentation.viewmodels.db.PlayersViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterScreenViewModel(
    private val playersVM: PlayersViewModel,
    private val coachesVM: CoachesViewModel
): ViewModel() {
    // Screen UI State
    private val _uiState = MutableStateFlow(RegisterScreenUiState())
    val uiState: StateFlow<RegisterScreenUiState> = _uiState.asStateFlow()

    private fun mailValidation(email:String): Boolean {
        return email.contains("@") && (email.contains(".com") || email.contains(".es"))
    }

    private fun fieldValidation(
        name: String, surname: String, birthdate: String, password: String
    ): Int {
        if (name.isBlank()) return 1
        else if (surname.isBlank()) return 2
        else if (birthdate.isBlank()) return 3
        else if (password.isBlank()) return 4
        return 0
    }

    private fun validateState(): Boolean {
        val state = _uiState.value
        return !state.nameFormatError
                && !state.emailFormatError
                && !state.surnameFormatError
                && !state.passwordFormatError
                && !state.birthdateFormatError
    }

    private fun sendMail() {

    }

    fun registerNewPlayer(
        name: String, surname: String, surname2: String, email: String, birthdate: String,
        password: String, teamcode: String, position: List<String>, number: Int
    ) {
        if (mailValidation(email)) {
            _uiState.update { it.copy(emailFormatError = false) }
            val fieldValidation = fieldValidation(name, surname, birthdate, password)
            when (fieldValidation) {
                1 -> _uiState.update { it.copy(nameFormatError = true) }
                2 -> _uiState.update { it.copy(surnameFormatError = true) }
                3 -> _uiState.update { it.copy(birthdateFormatError = true) }
                4 -> _uiState.update { it.copy(passwordFormatError = true) }
                0 -> _uiState.update {
                    it.copy(
                        nameFormatError = false,
                        surnameFormatError = false,
                        birthdateFormatError = false,
                        passwordFormatError = false
                    )
                }
            }
            if (teamcode.isBlank()) _uiState.update { it.copy(teamCodeFormatError = true) }
            else _uiState.update { it.copy(teamCodeFormatError = false) }
            if (teamcode != "0000") _uiState.update { it.copy(teamCodeFormatError = true) }
            else _uiState.update { it.copy(teamCodeFormatError = false) }
            if (validateState()) {
                sendMail()
                _uiState.update { it.copy(showConfirmMsg = true) }
            }
            else _uiState.update { it.copy(showConfirmMsg = false) }

        }
        else _uiState.update { it.copy(emailFormatError = true) }
    }

    fun registerNewCoach(
        name: String, surname: String, surname2: String, email: String, bithdate: String,
        password: String, teamcode: String, roles: List<String>
    ) {

    }
}

class RegisterScreenViewModelFactory(
    private val playersVM: PlayersViewModel,
    private val coachesVM: CoachesViewModel
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterScreenViewModel(playersVM, coachesVM,) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}