package com.example.basket4all.presentation.viewmodels.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.basket4all.data.local.entities.PlayerStats
import com.example.basket4all.data.local.entities.TeamEntity
import com.example.basket4all.presentation.uistate.AddPlayerScreenUiState
import com.example.basket4all.presentation.uistate.ProfileUiState
import com.example.basket4all.presentation.viewmodels.db.CoachesViewModel
import com.example.basket4all.presentation.viewmodels.db.PlayerStatsViewModel
import com.example.basket4all.presentation.viewmodels.db.PlayersViewModel
import com.example.basket4all.presentation.viewmodels.db.TeamViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val playersVM: PlayersViewModel,
    private val coachesVM: CoachesViewModel,
    private val playerStatsVM: PlayerStatsViewModel,
    private val teamVM: TeamViewModel,
    private val userId: Int,
    private val isPlayer: Boolean?
): ViewModel() {
    // Screen UI state
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        searchUser()
    }

    private fun searchUser() {
        Log.d("Search", "Buscando usuario")
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            when(isPlayer) {
                true -> {
                    Log.d("Search", "El usuario es un jugador")
                    val player = playersVM.getById(userId)
                    _uiState.update {
                        it.copy(
                            username = player.user.name,
                            surname = player.user.surname1,
                            number = player.number,
                            positions = player.getPositionsName(),
                            team = teamVM.getById(player.teamId),
                            stats = playerStatsVM.getByPlayerId(userId).first()
                        )
                    }
                }
                false -> {
                    Log.d("Search", "El usuario es un entrenador")
                    val coach = coachesVM.getById(userId)
                    _uiState.update {
                        it.copy(
                            username = coach.user.name,
                            surname = coach.user.surname1,
                            number = -1,
                            positions = coach.getCoachRoles(),
                            team = coachesVM.getTeams(coach.coachId)
                        )
                    }
                }
                else -> throw Exception("No se ha iniciado sesión correctamente")
            }
            delay(800)
            _uiState.update { it.copy(loading = false) }
            Log.d("Search", "Usuario encontrado")
        }
    }
}

class ProfileViewModelFactory(
    private val playersVM: PlayersViewModel,
    private val coachesVM: CoachesViewModel,
    private val playerStats: PlayerStatsViewModel,
    private val teamVM: TeamViewModel,
    private val userId: Int,
    private val isPlayer: Boolean?
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(playersVM, coachesVM, playerStats, teamVM, userId, isPlayer) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}