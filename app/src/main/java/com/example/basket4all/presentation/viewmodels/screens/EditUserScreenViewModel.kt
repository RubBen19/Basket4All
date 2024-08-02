package com.example.basket4all.presentation.viewmodels.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.basket4all.common.messengers.SessionManager
import com.example.basket4all.presentation.uistate.EditUserScreenUiState
import com.example.basket4all.presentation.viewmodels.db.CoachesViewModel
import com.example.basket4all.presentation.viewmodels.db.PlayersViewModel
import com.example.basket4all.presentation.viewmodels.db.TeamViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditUserScreenViewModel(
    private val playerVM: PlayersViewModel,
    private val coachesVM: CoachesViewModel,
    private val teamVM: TeamViewModel
): ViewModel() {

    // Screen UI State
    private val _uiState = MutableStateFlow(EditUserScreenUiState())
    val uiState: StateFlow<EditUserScreenUiState> = _uiState.asStateFlow()

    init {
        searchUser()
    }

    private fun searchUser() {
        Log.d("EditUserVM", "Buscando datos del usuario")
        val sessionMng = SessionManager.getInstance()
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true) }
            when(sessionMng.getRole()) {
                true -> {
                    val player = playerVM.getById(sessionMng.getUserId())
                    val userInfo = player.user
                    _uiState.update {
                        it.copy(
                            name = userInfo.name,
                            surname = userInfo.surname1,
                            surname2 = userInfo.surname2 ?: "",
                            email = userInfo.email,
                            number = player.number.toString()
                        )
                    }
                    Log.d("EditUserVM", "Datos de usuario cargados")
                }
                else -> {
                    val coach = coachesVM.getById(sessionMng.getUserId())
                    val userInfo = coach.user
                    _uiState.update {
                        it.copy(
                            name = userInfo.name,
                            surname = userInfo.surname1,
                            surname2 = userInfo.surname2 ?: "",
                            email = userInfo.email
                        )
                    }
                    Log.d("EditUserVM", "Datos de usuario cargados")
                }
            }
        }
        _uiState.update { it.copy(loading = false) }
        Log.d("EditUserVM", "Finalizando búsqueda")
    }

    fun update(type: String, newValue: String) {
        _uiState.update {
            when(type) {
                "Name" -> { it.copy(name = newValue)}
                "First Surname" -> { it.copy(surname = newValue)}
                "Second Surname" -> { it.copy(surname2 = newValue)}
                "Email" -> { it.copy(email = newValue)}
                "Dorsal" -> {
                    val number = newValue.toIntOrNull()
                    it.copy(number = number?.toString() ?: "")
                }
                else -> {it}
            }
        }
    }
}

class EditUserScreenViewModelFactory(
    private val playersVM: PlayersViewModel,
    private val coachesVM: CoachesViewModel,
    private val teamVM: TeamViewModel
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditUserScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditUserScreenViewModel(playersVM, coachesVM, teamVM) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}