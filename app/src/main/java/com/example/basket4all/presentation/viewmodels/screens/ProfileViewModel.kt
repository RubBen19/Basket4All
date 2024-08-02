package com.example.basket4all.presentation.viewmodels.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.basket4all.data.local.entities.User
import com.example.basket4all.presentation.uistate.ProfileUiState
import com.example.basket4all.presentation.viewmodels.db.CoachesViewModel
import com.example.basket4all.presentation.viewmodels.db.PlayerStatsViewModel
import com.example.basket4all.presentation.viewmodels.db.PlayersViewModel
import com.example.basket4all.presentation.viewmodels.db.TeamViewModel
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

    private val _user: MutableLiveData<User> = MutableLiveData(null)
    val user: LiveData<User> = _user

    init {
        searchUser()
    }

    private fun searchUser() {
        Log.d("ProfileVM", "Buscando usuario")
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            when(isPlayer) {
                true -> {
                    Log.d("ProfileVM", "El usuario es un jugador")
                    val player = playersVM.getById(userId)
                    _uiState.update {
                        it.copy(
                            username = player.user.name,
                            surname = player.user.surname1,
                            number = player.number,
                            image = player.user.picture,
                            positions = player.getPositionsName(),
                            team = teamVM.getById(player.teamId),
                            stats = playerStatsVM.getByPlayerId(userId).first()
                        )
                    }
                    _user.value = player.user
                }
                false -> {
                    Log.d("ProfileVM", "El usuario es un entrenador")
                    val coach = coachesVM.getById(userId)
                    _uiState.update {
                        it.copy(
                            username = coach.user.name,
                            surname = coach.user.surname1,
                            number = -1,
                            positions = coach.getCoachRoles(),
                            team = coachesVM.getTeams(coach.coachId),
                            image = coach.user.picture
                        )
                    }
                    _user.value = coach.user
                }
                else -> throw Exception("No se ha iniciado sesión correctamente")
            }
            _uiState.update { it.copy(loading = false) }
            Log.d("ProfileVM", "Usuario encontrado")
        }
    }

    fun changeImageSelectorVisibility() {
        _uiState.update { it.copy(imageSelectorVisible = !it.imageSelectorVisible) }
    }

    fun changeVisibilityOfInfo(){
        _uiState.update { it.copy(showInfo = !it.showInfo) }
    }
    fun changeProfileImage(image: ByteArray) {
        Log.d("ProfileVM", "Actualizando la imagen de perfil")
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            when(isPlayer) {
                true -> {
                    val player = playersVM.getById(userId)
                    player.user.picture = image
                    playersVM.update(player)
                    _uiState.update { it.copy(image = player.user.picture) }
                }
                false -> {
                    val coach = coachesVM.getById(userId)
                    coach.user.picture = image
                    coachesVM.update(coach)
                    _uiState.update { it.copy(image = coach.user.picture) }
                }
                else -> throw Exception("No se ha iniciado sesión correctamente")
            }
        }
        Log.d("ProfileVM", "¡Imagen de perfil actualizada!")
        _uiState.update { it.copy(loading = false) }
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