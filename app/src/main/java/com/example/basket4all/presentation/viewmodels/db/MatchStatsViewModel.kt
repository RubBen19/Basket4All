package com.example.basket4all.presentation.viewmodels.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.basket4all.data.local.daos.MatchStatsDao
import com.example.basket4all.data.local.entities.MatchStats
import kotlinx.coroutines.flow.first

class MatchStatsViewModel(private val matchStatsDao: MatchStatsDao): ViewModel() {

    suspend fun insert(matchStats: MatchStats) {
        matchStatsDao.insert(matchStats)
    }

    suspend fun playerMatchStats(playerId: Int): List<MatchStats> {
        return matchStatsDao.getByPlayer(playerId).first()
    }
}

class MatchStatsViewModelFactory(private val matchStatsDao: MatchStatsDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MatchStatsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MatchStatsViewModel(matchStatsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}