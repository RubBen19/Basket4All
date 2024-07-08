package com.example.basket4all.presentation.viewmodels.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.basket4all.presentation.uistate.TeamScreenUiState
import com.example.basket4all.presentation.viewmodels.db.TeamStatsViewModel
import com.example.basket4all.presentation.viewmodels.db.TeamViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TeamScreenViewModel(
    private val teamVM: TeamViewModel,
    private val teamStatsVM: TeamStatsViewModel,
    private val teamId: Int,
): ViewModel() {

    private val _uiState = MutableStateFlow(TeamScreenUiState())
    val uiState: StateFlow<TeamScreenUiState> = _uiState

    init {
        searchTeam()
    }

    private fun searchTeam() {
        Log.d("TeamScreenViewModel", "Buscando equipo")
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            val stats = teamStatsVM.getByTeamId(teamId)
            _uiState.update {
                it.copy(
                    team = teamVM.getById(teamId),
                    players = teamVM.getPlayers(teamId),
                    wins = stats.wins,
                    defeats = stats.matchPlayed - it.wins,
                    points = stats.points.toDouble() / stats.matchPlayed
                )
            }
            _uiState.update { it.copy(loading = false) }
            Log.d("TeamScreenViewModel", "Equipo encontrado")
        }
    }
}

class TeamScreenViewModelFactory(
    private val teamVM: TeamViewModel,
    private val teamStatsVM: TeamStatsViewModel,
    private val teamId: Int
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TeamScreenViewModel(teamVM, teamStatsVM, teamId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}