package com.example.basket4all.presentation.viewmodels.screens

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.basket4all.common.enums.CoachRoles
import com.example.basket4all.common.enums.PlayerPositions
import com.example.basket4all.common.messengers.EmailManager
import com.example.basket4all.data.local.entities.CoachEntity
import com.example.basket4all.data.local.entities.PlayerEntity
import com.example.basket4all.data.local.entities.User
import com.example.basket4all.presentation.uistate.RegisterScreenUiState
import com.example.basket4all.presentation.viewmodels.db.CoachesViewModel
import com.example.basket4all.presentation.viewmodels.db.PlayersViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

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

    fun getErrorMessage() {
        var error = ""
        if (_uiState.value.emailFormatError) error += "Revisa tu email "
        if (_uiState.value.nameFormatError) error += "Introduce un nombre "
        if (_uiState.value.surnameFormatError) error += "Introduce tu apellido "
        if (_uiState.value.passwordFormatError) error += "Introduce tu contraseña "
        if (_uiState.value.birthdateFormatError) error += "Introduce tu fecha de nacimiento "
        if (_uiState.value.teamCodeFormatError) error += "Código de equipo no existente."
        _uiState.update { it.copy(msg = error) }
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

    private suspend fun sendMail(email: String, name:String, context: Context) {
        val emailManager = EmailManager(context)
        emailManager.welcomeEmail(to = email, name = name)
    }

    fun registerNewPlayer(
        name: String, surname: String, surname2: String, email: String, birthdate: String,
        password: String, teamcode: String, position: List<String>, number: Int, context: Context
    ) {
        if (mailValidation(email)) {
            _uiState.update { it.copy(emailFormatError = false) }
            val fieldValidation = fieldValidation(name, surname, birthdate, password)
            when (fieldValidation) {
                1 -> _uiState.update {
                    it.copy(
                        nameFormatError = true,
                        surnameFormatError = false,
                        birthdateFormatError = false,
                        passwordFormatError = false
                    )
                }
                2 -> _uiState.update {
                    it.copy(
                        nameFormatError = false,
                        surnameFormatError = true,
                        birthdateFormatError = false,
                        passwordFormatError = false
                    )
                }
                3 -> _uiState.update {
                    it.copy(
                        nameFormatError = false,
                        surnameFormatError = false,
                        birthdateFormatError = true,
                        passwordFormatError = false
                    )
                }
                4 -> _uiState.update {
                    it.copy(
                        nameFormatError = false,
                        surnameFormatError = false,
                        birthdateFormatError = false,
                        passwordFormatError = true
                    )
                }
                0 -> _uiState.update {
                    it.copy(
                        nameFormatError = false,
                        surnameFormatError = false,
                        birthdateFormatError = false,
                        passwordFormatError = false
                    )
                }
            }
            if (teamcode.isBlank() || teamcode != "0000")
                _uiState.update { it.copy(teamCodeFormatError = true) }
            else _uiState.update { it.copy(teamCodeFormatError = false) }
            if (validateState()) {
                // Inserccion de jugador y envio de correo
                viewModelScope.launch {
                    val dateArray = birthdate.split("/").toTypedArray()
                    val dateInFormat = LocalDate.of(dateArray[2].toInt(), dateArray[1].toInt(), dateArray[0].toInt())
                    val positionsList = position
                        .filter { it in PlayerPositions.values().map { pos -> pos.name } }
                        .map { PlayerPositions.valueOf(it) }
                        .toMutableList()
                    val newUser = User(email, password, name, surname, surname2, dateInFormat)
                    val newPlayer = PlayerEntity(
                        user = newUser,
                        teamId = 1,
                        positions = positionsList,
                        number = number
                    )
                    playersVM.insert(newPlayer)
                    sendMail(email, name, context)
                }
                _uiState.update { it.copy(showConfirmMsg = true) }
            }
            else _uiState.update { it.copy(showConfirmMsg = false) }
        }
        else _uiState.update { it.copy(emailFormatError = true) }
    }

    fun registerNewCoach(
        name: String, surname: String, surname2: String, email: String, birthdate: String,
        password: String, teamcode: String, roles: List<String>, context: Context
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
            if (teamcode.isBlank() || teamcode != "0000")
                _uiState.update { it.copy(teamCodeFormatError = true) }
            else _uiState.update { it.copy(teamCodeFormatError = false) }
            if (validateState()) {
                viewModelScope.launch {
                    val dateArray = birthdate.split("/").toTypedArray()
                    val dateInFormat = LocalDate.of(dateArray[2].toInt(), dateArray[1].toInt(), dateArray[0].toInt())
                    val rolesList = roles
                        .filter { it in CoachRoles.values().map { role -> role.name } }
                        .map { CoachRoles.valueOf(it) }
                        .toMutableList()
                    val newUser = User(email, password, name, surname, surname2, dateInFormat)
                    val newCoach = CoachEntity(
                        user = newUser,
                        coachroles = rolesList
                    )
                    coachesVM.insert(newCoach)
                    sendMail(email, name, context)
                }
                _uiState.update { it.copy(showConfirmMsg = true) }
            }
            else _uiState.update { it.copy(showConfirmMsg = false) }

        }
        else _uiState.update { it.copy(emailFormatError = true) }
    }
}

class RegisterScreenViewModelFactory(
    private val playersVM: PlayersViewModel,
    private val coachesVM: CoachesViewModel
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterScreenViewModel(playersVM, coachesVM) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}