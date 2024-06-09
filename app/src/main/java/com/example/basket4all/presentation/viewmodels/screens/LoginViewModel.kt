package com.example.basket4all.presentation.viewmodels.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.basket4all.common.messengers.SessionManager
import com.example.basket4all.presentation.viewmodels.db.CoachesViewModel
import com.example.basket4all.presentation.viewmodels.db.PlayersViewModel
import kotlinx.coroutines.launch

class LoginViewModel(
    private val playersVM: PlayersViewModel,
    private val coachesVM: CoachesViewModel
): ViewModel() {
    // Variables utilizadas en la pantalla de login
    private val _email: MutableLiveData<String> = MutableLiveData("")
    val email: LiveData<String> = _email
    private val _password: MutableLiveData<String> = MutableLiveData("")
    val password: LiveData<String> = _password
    private val _option: MutableLiveData<String> = MutableLiveData("")
    val option: LiveData<String> = _option
    private var _login: MutableLiveData<Boolean?> = MutableLiveData(null)
    var login: LiveData<Boolean?> = _login
    // Instancia del administrador de la sesión
    private val session: SessionManager = SessionManager.getInstance()

    /*** LOGIN ***/
    fun login() {
        viewModelScope.launch {
            val mail = email.value ?: ""
            val pass = password.value ?: ""
            when(option.value) {
                "Jugador" -> {
                    val player = playersVM.getByEmail(mail)
                    if (player.user.password == pass) {
                        session.login(player.id, player.teamId, true)
                        _login.value = true
                    }
                }
                "Entrenador" -> {
                    val coach = coachesVM.getByEmail(mail)
                    if (coach.user.password == pass) {
                        session.login(coach.coachId, 1, false)
                        _login.value = true
                    }
                }
            }
        }
    }

    fun resetLogin() {
        _login.value = null
    }

    /*** E-MAIL ***/
    fun changeEmail(mail: String) {
        _email.value = mail
    }

    /*** PASSWORD ***/
    fun changePassword(pass: String) {
        _password.value = pass
    }

    /*** OPTION ***/
    fun changeOption(opt: String) {
        _option.value = opt
    }
}

class LoginViewModelFactory(
    private val playersVM: PlayersViewModel,
    private val coachesVM: CoachesViewModel
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(playersVM, coachesVM) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}