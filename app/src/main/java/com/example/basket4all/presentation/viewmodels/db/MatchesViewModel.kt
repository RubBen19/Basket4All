package com.example.basket4all.presentation.viewmodels.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.basket4all.data.local.daos.MatchDao
import com.example.basket4all.data.local.entities.MatchEntity
import kotlinx.coroutines.flow.first

class MatchesViewModel(private val matchDao: MatchDao): ViewModel() {

    suspend fun getMatchesByTeamId(id: Int): List<MatchEntity> {
        return matchDao.getByTeam(id).first()
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