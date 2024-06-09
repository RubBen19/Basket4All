package com.example.basket4all.presentation.viewmodels.screens

import android.util.Log
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.basket4all.data.local.entities.PlayerEntity
import com.example.basket4all.data.local.entities.TeamEntity
import com.example.basket4all.presentation.viewmodels.db.TeamStatsViewModel
import com.example.basket4all.presentation.viewmodels.db.TeamViewModel
import kotlinx.coroutines.launch

class TeamScreenViewModel(
    private val teamVM: TeamViewModel,
    private val teamStatsVM: TeamStatsViewModel,
    private val teamId: Int,
): ViewModel() {
    private val _team = MutableLiveData<TeamEntity>()
    val team: LiveData<TeamEntity> = _team
    private val _players = MutableLiveData<List<PlayerEntity>>()
    val players: LiveData<List<PlayerEntity>> = _players
    // Variables relacionadas con las estadísticas
    private val _defeats = MutableLiveData<Int>()
    val defeats: LiveData<Int> = _defeats
    private val _wins = MutableLiveData<Int>()
    val wins: LiveData<Int> = _wins
    private val _points = MutableLiveData<Double>()
    val points: LiveData<Double> = _points


    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    init {
        searchTeam()
    }

    private fun searchTeam() {
        Log.d("TeamScreenViewModel", "Buscando equipo")
        _loading.value = true
        viewModelScope.launch {
            _team.value = teamVM.getById(teamId)
            _players.value = teamVM.getPlayers(teamId)

            val stats = teamStatsVM.getByTeamId(teamId)
            _wins.value = stats.wins
            _defeats.value = stats.matchPlayed - _wins.value!!
            _points.value = stats.points.toDouble() / stats.matchPlayed

            _loading.value = false
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