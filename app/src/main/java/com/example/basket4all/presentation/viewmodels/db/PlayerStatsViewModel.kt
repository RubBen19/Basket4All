package com.example.basket4all.presentation.viewmodels.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.basket4all.data.local.daos.MatchDao
import com.example.basket4all.data.local.daos.PlayerStatsDao
import com.example.basket4all.data.local.entities.PlayerStats
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class PlayerStatsViewModel(private val playerStatsDao: PlayerStatsDao): ViewModel() {

    suspend fun insert(playerId: Int) {
        val newStats = PlayerStats(playerId = playerId)
        playerStatsDao.insert(newStats)
    }

    suspend fun getByPlayerId(id: Int): List<PlayerStats> {
        return playerStatsDao.getByPlayer(id).first()
    }
}

class PlayerStatsViewModelFactory(private val playerStatsDao: PlayerStatsDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlayerStatsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlayerStatsViewModel(playerStatsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}