package com.example.basket4all.presentation.viewmodels.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.basket4all.data.local.daos.PlayerDao
import com.example.basket4all.data.local.entities.PlayerEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class PlayersViewModel(private val playerDao: PlayerDao): ViewModel() {

    init {
        viewModelScope.launch {
            val player: PlayerEntity = playerDao.getByID(1).first()
        }
    }

    suspend fun insert(player: PlayerEntity) {
        playerDao.insert(player)
    }

    suspend fun getByEmail(email: String): PlayerEntity? {
        return playerDao.getByEmail(email).firstOrNull()
    }

    suspend fun getById(id: Int): PlayerEntity {
        return playerDao.getByID(id).first()
    }

    suspend fun update(player: PlayerEntity) {
        playerDao.update(player)
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