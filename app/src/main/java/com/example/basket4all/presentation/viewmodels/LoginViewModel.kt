package com.example.basket4all.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.basket4all.data.local.daos.CoachDao
import com.example.basket4all.data.local.daos.PlayerDao

class LoginViewModel(private val playerDao: PlayerDao,
                     private val coachDao: CoachDao) : ViewModel()
{

    private fun playerVerify(email: String, password: String): Boolean {
        val playerData = playerDao.getByEmail(email).asLiveData()
        if (playerData.isInitialized && playerData.value?.user?.password == password) {
            return true
        }
        return false
    }

    private fun coachVerify(email: String, password: String): Boolean {
        val coachData = coachDao.getByEmail(email).asLiveData()
        if (coachData.isInitialized && coachData.value?.user?.password == password) {
            return true
        }
        return false
    }

    // Función para que un usuario inicie la sesión
    fun userLogin(email: String, password: String): Boolean {
        val isPlayer = playerVerify(email, password)
        val isCoach = coachVerify(email, password)
        return isPlayer || isCoach
    }
}

class LoginViewModelFactory(private val playerDao: PlayerDao,
                            private val coachDao: CoachDao) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(playerDao, coachDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}