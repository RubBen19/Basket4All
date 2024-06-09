package com.example.basket4all.presentation.viewmodels.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.basket4all.data.local.entities.PlayerStats
import com.example.basket4all.data.local.entities.TeamEntity
import com.example.basket4all.presentation.viewmodels.db.CoachesViewModel
import com.example.basket4all.presentation.viewmodels.db.PlayerStatsViewModel
import com.example.basket4all.presentation.viewmodels.db.PlayersViewModel
import com.example.basket4all.presentation.viewmodels.db.TeamViewModel
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val playersVM: PlayersViewModel,
    private val coachesVM: CoachesViewModel,
    private val playerStatsVM: PlayerStatsViewModel,
    private val teamVM: TeamViewModel,
    private val userId: Int,
    private val isPlayer: Boolean?
): ViewModel() {
    // Variables
    private val _username = MutableLiveData<String>(" ")
    val username: LiveData<String> get() = _username
    private val _surname = MutableLiveData<String>(" ")
    val surname: LiveData<String> get() = _surname
    private val _number = MutableLiveData<Int>(0)
    val number: LiveData<Int> get() = _number
    private val _positions = MutableLiveData<List<String>>()
    val positions: LiveData<List<String>> get() = _positions

    private val _team = MutableLiveData<TeamEntity>()
    val team: LiveData<TeamEntity> get() = _team

    private val _stats = MutableLiveData<PlayerStats>()
    val stats: LiveData<PlayerStats> get() = _stats

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    init {
        searchUser()
    }

    private fun searchUser() {
        Log.d("Search", "Buscando usuario")
        _loading.value = true
        viewModelScope.launch {
            when(isPlayer) {
                true -> {
                    Log.d("Search", "El usuario es un jugador")
                    val player = playersVM.getById(userId)
                    _username.value = player.user.name
                    _surname.value = player.user.surname1
                    _number.value = player.number
                    _positions.value = player.getPositionsName()
                    _team.value = teamVM.getById(player.teamId)
                    _stats.value = playerStatsVM.getByPlayerId(userId).first()
                }
                false -> {
                    Log.d("Search", "El usuario es un entrenador")
                    val coach = coachesVM.getById(userId)
                    _username.value = coach.user.name
                    _surname.value = coach.user.surname1
                    _number.value = -1
                    _positions.value = coach.getCoachRoles()
                    _team.value = coachesVM.getTeams(coach.coachId)
                }
                else -> throw Exception("No se ha iniciado sesión correctamente")
            }
            _loading.value = false
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