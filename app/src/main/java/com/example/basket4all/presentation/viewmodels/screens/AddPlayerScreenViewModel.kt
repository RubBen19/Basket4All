package com.example.basket4all.presentation.viewmodels.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.basket4all.common.classes.PlayerStatsClass
import com.example.basket4all.presentation.uistate.AddPlayerScreenUiState
import com.example.basket4all.presentation.viewmodels.db.PlayersViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddPlayerScreenViewModel(
    private val playersVM: PlayersViewModel,
    private val playerId: Int
) : ViewModel() {
    // Screen UI state
    private val _uiState = MutableStateFlow(AddPlayerScreenUiState())
    val uiState: StateFlow<AddPlayerScreenUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val player = playersVM.getById(playerId)
            _uiState.update {
                it.copy(
                    loading = true,
                    name = player.user.name,
                    surname = player.user.surname1,
                    surname2 = player.user.surname2.toString()
                )
            }
            _uiState.update { it.copy(loading = false) }
        }
    }

    fun insert(stats: PlayerStatsClass) {
        stats.id = playerId
        _uiState.value.newMatchCourier.addPlayer(playerId, stats)
    }

    fun show(popupName: String) {
        when(popupName) {
            "Minutos" -> _uiState.update { it.copy(showMinutes = !it.showMinutes) }
            "Tiros de 2" -> _uiState.update { it.copy(showShots2 = !it.showShots2) }
            "Tiros de 3" -> _uiState.update { it.copy(showShots3 = !it.showShots3) }
            "Tiros libres" -> _uiState.update { it.copy(showFShots = !it.showFShots) }
            "Asistencias" -> _uiState.update { it.copy(showAssist = !it.showAssist) }
            "Rebotes" -> _uiState.update { it.copy(showRebounds = !it.showRebounds) }
            "Tapones" -> _uiState.update { it.copy(showBlocks = !it.showBlocks) }
            "Faltas" -> _uiState.update { it.copy(showFaults = !it.showFaults) }
            "Robos" -> _uiState.update { it.copy(showSteals = !it.showSteals) }
            "Pérdidas" -> _uiState.update { it.copy(showLosses = !it.showLosses) }
        }
    }
}

class AddPlayerScreenViewModelFactory(
    private val playersVM: PlayersViewModel,
    private val playerId: Int
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddPlayerScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddPlayerScreenViewModel(playersVM, playerId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}