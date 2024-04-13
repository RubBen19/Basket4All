package com.example.basket4all.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.basket4all.data.local.daos.PlayerDao
import com.example.basket4all.data.local.entities.PlayerEntity

class PlayersViewModel(private val playerDao: PlayerDao): ViewModel() {
    val allPlayers: LiveData<List<PlayerEntity>> = playerDao.getAll().asLiveData()

    fun getByEmail(email: String): LiveData<PlayerEntity?> {
        return playerDao.getByEmail(email).asLiveData()
    }
}

class PlayersViewModelFactory(private val playerDao: PlayerDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlayersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlayersViewModel(playerDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}