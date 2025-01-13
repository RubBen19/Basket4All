package com.example.basket4all.presentation.viewmodels.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.basket4all.data.local.daos.CoachDao
import com.example.basket4all.data.local.entities.CoachEntity
import com.example.basket4all.data.local.entities.TeamEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class CoachesViewModel(private val coachDao: CoachDao): ViewModel() {

    suspend fun insert(coach: CoachEntity) {
        coachDao.insert(coach)
    }

    suspend fun getByEmail(email: String): CoachEntity? {
        return coachDao.getByEmail(email).firstOrNull()
    }

    suspend fun getById(id: Int): CoachEntity {
        return coachDao.getByID(id).first()
    }

    suspend fun getTeams(id: Int): TeamEntity {
        return coachDao.getCoachesWithTeams().first().first { it.coach.coachId == id }.teams.first()
    }

    suspend fun update(coach: CoachEntity) {
        coachDao.update(coach)
    }
}

class CoachesViewModelFactory(private val coachDao: CoachDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoachesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CoachesViewModel(coachDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}