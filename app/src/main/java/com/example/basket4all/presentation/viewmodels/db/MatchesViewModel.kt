package com.example.basket4all.presentation.viewmodels.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.basket4all.common.classes.Score
import com.example.basket4all.data.local.daos.MatchDao
import com.example.basket4all.data.local.entities.MatchEntity
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate

class MatchesViewModel(private val matchDao: MatchDao): ViewModel() {

    suspend fun getMatchesByTeamId(id: Int): List<MatchEntity> {
        return matchDao.getByTeam(id).first()
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
    suspend fun searchEqualMatch(localId: Int, visitorId: Int, date: LocalDate, score: Score): MatchEntity? {
        return matchDao.getAll().first().find { it.date == date
                && it.localTeamId == localId
                && it.visitorTeamId == visitorId
                && it.score.getLocalScore() == score.getLocalScore()
                && it.score.getVisitorScore() == score.getVisitorScore()
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