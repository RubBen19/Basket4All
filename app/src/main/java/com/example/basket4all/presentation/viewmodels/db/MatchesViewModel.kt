package com.example.basket4all.presentation.viewmodels.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.basket4all.common.classes.MatchScore
import com.example.basket4all.common.classes.Score
import com.example.basket4all.data.local.daos.MatchDao
import com.example.basket4all.data.local.entities.MatchEntity
import com.example.basket4all.data.local.relations.MatchWithMatchStats
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate

class MatchesViewModel(private val matchDao: MatchDao): ViewModel() {

    suspend fun getById(id: Int): MatchEntity {
        return matchDao.getByID(id).first()
    }

    suspend fun getMatchesByTeamId(id: Int): List<MatchEntity> {
        return matchDao.getByTeam(id).first()
    }

    suspend fun getWithStats(id: Int): MatchWithMatchStats {
        return matchDao.getMatchesWithStats().first().first { it.match.id == id }
    }

    fun update(match: MatchEntity) {
        viewModelScope.launch {
            matchDao.update(match)
        }
    }

    fun insertMatch(match: MatchEntity) {
        viewModelScope.launch {
            matchDao.insert(match)
        }
    }

    suspend fun searchMatch(localId: Int, visitorId: Int, date: LocalDate): MatchEntity? {
        return matchDao.getAll().first().find {
            it.date == date && it.localTeamId == localId && it.visitorTeamId == visitorId
        }
    }
    suspend fun searchEqualMatch(localId: Int, visitorId: Int, date: LocalDate, score: MatchScore): MatchEntity? {
        return matchDao.getAll().first().find { it.date == date
                && it.localTeamId == localId
                && it.visitorTeamId == visitorId
                && it.score.getScore() == score.getScore()
        }
    }
}

class MatchesViewModelFactory(private val matchDao: MatchDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MatchesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MatchesViewModel(matchDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}