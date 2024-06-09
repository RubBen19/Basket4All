package com.example.basket4all.presentation.viewmodels.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.basket4all.common.enums.Categories
import com.example.basket4all.data.local.daos.TeamDao
import com.example.basket4all.data.local.entities.PlayerEntity
import com.example.basket4all.data.local.entities.TeamEntity
import kotlinx.coroutines.flow.first

class TeamViewModel(private val teamDao: TeamDao): ViewModel() {

    suspend fun getById(id: Int): TeamEntity {
        return teamDao.getByID(id).first()
    }

    suspend fun getPlayers(id: Int): List<PlayerEntity> {
        return teamDao.getTeamsWithPlayers().first().first { it.team.teamId == id }.players
    }

    suspend fun getByCategory(category: Categories): List<TeamEntity> {
        return teamDao.getByCategory(category = category).first()
    }

    suspend fun getByLigue(league: String): List<TeamEntity> {
        return teamDao.getByLeague(league).first()
    }

    suspend fun getByLigueAndCategory(category: Categories, league: String): List<TeamEntity> {
        return teamDao.geyByCategoryAndLeague(category, league).first()
    }

}

class TeamViewModelFactory(private val teamDao: TeamDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TeamViewModel(teamDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}