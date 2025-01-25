package com.example.basket4all.presentation.viewmodels.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.basket4all.data.local.entities.MatchEntity
import com.example.basket4all.presentation.uistate.MatchScreenUiState
import com.example.basket4all.presentation.viewmodels.db.MatchesViewModel
import com.example.basket4all.presentation.viewmodels.db.PlayersViewModel
import com.example.basket4all.presentation.viewmodels.db.TeamViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MatchScreenViewModel(
    private val playersVM: PlayersViewModel,
    private val matchesVM: MatchesViewModel,
    private val teamVM: TeamViewModel,
    private val matchID: Int
): ViewModel() {
    private val _uiState = MutableStateFlow(MatchScreenUiState())
    val uiState: StateFlow<MatchScreenUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            var match: MatchEntity? = null
            _uiState.update { it.copy(loading = true) }
            match = getMatch()
            _uiState.update { it.copy(loading = false) }
        }
    }

    private suspend fun getMatch(): MatchEntity? {
        var match: MatchEntity? = null
        match = matchesVM.getById(matchID)
        if (match != null) {
            val score = match.score
            _uiState.update {
                it.copy(
                    score = score.getScore(),
                    date = match.date.toString(),
                    scoreQ1 = score.getScore(1),
                    scoreQ2 = score.getScore(2),
                    scoreQ3 = score.getScore(3),
                    scoreQ4 = score.getScore(4)
                )
            }
        }
        else _uiState.update { it.copy(date = "PARTIDO NO ENCONTRADO") }
        return match
    }

    private suspend fun getPlayers() {
        val playerStats = matchesVM.getWithStats(matchID).matchesStats
        var maxPP: Pair<Int, Int> = Pair(-1, 0)
        var maxAP: Pair<Int, Int> = Pair(-1, 0)
        var maxRP: Pair<Int, Int> = Pair(-1, 0)
        var shooterP: Pair<Int, Int> = Pair(-1, 0)
        var maxSP: Pair<Int, Int> = Pair(-1, 0)

        playerStats.forEach { player ->
            val points = player.stats.shots.getPoints()
            val assists = player.stats.lastPass.getAssist()
            val rebounds = player.stats.rebounds.getTotal()
            val shooter = player.stats.shots.get3Shots().getSuccess()

            if (points > maxPP.second) maxPP = Pair(player.id, points)
            if (assists > maxAP.second) maxAP = Pair(player.id, assists)
            if (rebounds > maxRP.second) maxRP = Pair(player.id, rebounds)
            if (shooter  > shooterP.second) shooterP = Pair(player.id, shooter)
            if (player.stats.steals > maxSP.second) maxSP = Pair(player.id, player.stats.steals)
        }

        if (maxPP.first != -1) _uiState.update { it.copy (maxPP = playersVM.getById(maxPP.first)) }
        if (maxAP.first != -1) _uiState.update { it.copy (maxAP = playersVM.getById(maxAP.first)) }
        if (maxRP.first != -1) _uiState.update { it.copy (maxRP = playersVM.getById(maxRP.first)) }
        if (shooterP.first != -1) _uiState.update { it.copy (shooterP = playersVM.getById(shooterP.first)) }
        if (maxSP.first != -1) _uiState.update { it.copy (maxSP = playersVM.getById(maxSP.first)) }
    }
}

class MatchScreenViewModelFactory(
    private val playerVM: PlayersViewModel,
    private val matchesVM: MatchesViewModel,
    private val teamVM: TeamViewModel,
    private val matchId: Int
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MatchScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MatchScreenViewModel(playerVM, matchesVM, teamVM, matchId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}