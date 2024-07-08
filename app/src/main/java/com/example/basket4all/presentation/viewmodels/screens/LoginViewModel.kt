package com.example.basket4all.presentation.viewmodels.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.basket4all.common.messengers.SessionManager
import com.example.basket4all.presentation.uistate.LoginUiState
import com.example.basket4all.presentation.viewmodels.db.CoachesViewModel
import com.example.basket4all.presentation.viewmodels.db.PlayersViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val playersVM: PlayersViewModel,
    private val coachesVM: CoachesViewModel
): ViewModel() {
    // Screen UI state
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // Instancia del administrador de la sesión
    private val session: SessionManager = SessionManager.getInstance()

    /*** LOGIN ***/
    fun login() {
        viewModelScope.launch {
            val mail = _uiState.value.email
            val pass = _uiState.value.password
            if (mail.isNotEmpty() && pass.isNotEmpty()) {
                when(_uiState.value.option) {
                    "Jugador" -> {
                        val player = playersVM.getByEmail(mail)
                        if (player != null && player.user.password == pass) {
                            session.login(player.id, player.teamId, true)
                            _uiState.update { it.copy(login = true, loginError = false) }
                        }
                        else _uiState.update { it.copy(loginError = true) }
                    }
                    "Entrenador" -> {
                        val coach = coachesVM.getByEmail(mail)
                        if (coach != null && coach.user.password == pass) {
                            session.login(coach.coachId, 1, false)
                            _uiState.update { it.copy(login = true, loginError = false) }
                        }
                        else _uiState.update { it.copy(loginError = true) }
                    }
                }
            }
            else _uiState.update { it.copy(loginError = true) }
        }
    }

    fun resetLogin() {
        _uiState.update { it.copy(login = false) }
    }

    /*** E-MAIL ***/
    fun changeEmail(mail: String) {
        _uiState.update { it.copy(email = mail) }
    }

    /*** PASSWORD ***/
    fun changePassword(pass: String) {
        _uiState.update { it.copy(password = pass) }
    }

    /*** OPTION ***/
    fun changeOption(opt: String) {
        _uiState.update { it.copy(option = opt) }
    }

    /*** PASSWORD VISIBILITY***/
    fun changeShowPassword() {
        _uiState.update { it.copy(hidden = !_uiState.value.hidden) }
    }
}

class LoginViewModelFactory(
    private val playersVM: PlayersViewModel,
    private val coachesVM: CoachesViewModel
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(playersVM, coachesVM) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}