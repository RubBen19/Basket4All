package com.example.basket4all.presentation.viewmodels.screens

import PlayerStatsUiScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.basket4all.presentation.viewmodels.db.MatchStatsViewModel
import com.example.basket4all.presentation.viewmodels.db.PlayerStatsViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlayerStatsScreenViewModel(
    private val matchStatsVM: MatchStatsViewModel,
    private val playerStatsVM: PlayerStatsViewModel,
    private val playerID: Int
): ViewModel() {
    // Screen UI state
    private val _uiState = MutableStateFlow(PlayerStatsUiScreen())
    val uiState: StateFlow<PlayerStatsUiScreen> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true) }
            getPlayerStats()
            _uiState.update { it.copy(loading = true) }
        }
    }

    private suspend fun getPlayerStats() {
        val stats = playerStatsVM.getByPlayerId(playerID).stats
        val games = stats.games
        val shotsOf2 = stats.shots.get2Shots()
        val shotsOf3 = stats.shots.get3Shots()
        val zoneShots = stats.shots.getZoneShots()
        val freeShots = stats.shots.getFreeShots()
        val keyPasses = stats.lastPass

        _uiState.update {
            it.copy(
                // Estadísticas por partido
                ppp = (stats.shots.getPoints() / games).toFloat(),
                mpp = (stats.minutes / games).toFloat(),
                app = (stats.lastPass.getAssist().toFloat() / games),
                rpp = (stats.rebounds.getRebounds() / games).toFloat(),
                lpp = (stats.losts / games).toFloat(),
                spp = (stats.steals / games).toFloat(),
                bpp = (stats.block.getBlocks() / games).toFloat(),
                // Estadísticas de tiro general
                // Tiro de 2
                twoPIn = shotsOf2.getSuccess(),
                twoPOut = shotsOf2.getFailed(),
                twoPShoots = shotsOf2.getShots(),
                twoPPercent = (shotsOf2.getSuccess().toFloat() / shotsOf2.getShots()) * 100,
                // Tiro de 3
                ThreePIn = shotsOf3.getSuccess(),
                ThreePOut = shotsOf3.getFailed(),
                ThreePShoots = shotsOf3.getShots(),
                ThreePPercent = (shotsOf3.getSuccess().toFloat() / shotsOf3.getShots()) * 100,
                // Tiros libres
                FpIn = freeShots.getSuccess(),
                FpOut = freeShots.getFailed(),
                FpShoots = freeShots.getTotal(),
                FpPercent = (freeShots.getSuccess().toFloat() / freeShots.getTotal()) * 100,
                // Tiros desde la zona
                ZpIn = zoneShots.getSuccess(),
                ZpOut = zoneShots.getFailed(),
                ZpShoots = zoneShots.getShots(),
                ZpPercent = (zoneShots.getSuccess().toFloat() / freeShots.getTotal()) * 100,
                // Estadísticas de pases clave
                totalPasses = keyPasses.getTotal(),
                assist = keyPasses.getAssist(),
                probAssist = (keyPasses.getAssist().toFloat() / keyPasses.getTotal()) * 100,
                // Estadísticas de rebotes
                offensiveReb = stats.rebounds.getOfRebound(),
                defensiveReb = stats.rebounds.getDefRebound(),
                // Otras
                fouls = stats.faults.getTotal(),
                losts = stats.losts,
                steals = stats.steals,
                blocks = stats.block.getBlocks(),
                // Lista de partidos
                matchesPlayed = matchStatsVM.playerMatchStats(playerID)
            )
        }
    }
}

class PlayerStatsScreenViewModelFactory(
    private val matchStatsVM: MatchStatsViewModel,
    private val playerStatsVM: PlayerStatsViewModel,
    private val playerID: Int
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlayerStatsScreenViewModel(matchStatsVM, playerStatsVM, playerID) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}