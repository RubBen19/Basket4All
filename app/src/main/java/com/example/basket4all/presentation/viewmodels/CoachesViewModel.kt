package com.example.basket4all.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.basket4all.data.local.daos.CoachDao
import com.example.basket4all.data.local.entities.CoachEntity

class CoachesViewModel(private val coachDao: CoachDao): ViewModel() {
    val allCoaches: LiveData<List<CoachEntity>> = coachDao.getAll().asLiveData()

    fun getByEmail(email: String): CoachEntity? {
        return coachDao.getByEmail(email).asLiveData().value
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