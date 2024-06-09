package com.example.basket4all.presentation.viewmodels.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.basket4all.data.local.daos.TeamStatsDao
import com.example.basket4all.data.local.entities.TeamStats
import kotlinx.coroutines.flow.first

class TeamStatsViewModel(private val teamStatsDao: TeamStatsDao): ViewModel() {
    suspend fun getByTeamId(id: Int): TeamStats {
        return teamStatsDao.getByTeam(id).first()
    }
}

class TeamStatsViewModelFactory(private val teamStatsDao: TeamStatsDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamStatsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TeamStatsViewModel(teamStatsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}