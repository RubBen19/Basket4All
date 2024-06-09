package com.example.basket4all.presentation.viewmodels.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.basket4all.common.classes.PlayerStatsClass
import com.example.basket4all.common.messengers.NewMatchCourier
import com.example.basket4all.data.local.entities.MatchStats
import com.example.basket4all.presentation.viewmodels.db.PlayersViewModel
import kotlinx.coroutines.launch

class AddPlayerScreenViewModel(
    private val playersVM: PlayersViewModel,
    private val playerId: Int
) : ViewModel() {
    // Variables de la screen
    private val _name: MutableLiveData<String> = MutableLiveData("")
    val name: LiveData<String> = _name
    private val _surname: MutableLiveData<String> = MutableLiveData("")
    val surname: LiveData<String> = _surname
    private val _surname2: MutableLiveData<String> = MutableLiveData("")
    val surname2: LiveData<String> = _surname2

    //Variables para los pop-up
    private val _showShots2 = MutableLiveData(false)
    val showShots2: LiveData<Boolean> = _showShots2
    private val _showShots3 = MutableLiveData(false)
    val showShots3: LiveData<Boolean> = _showShots3
    private val _showFShots = MutableLiveData(false)
    val showFShots: LiveData<Boolean> = _showFShots
    private val _showAssist = MutableLiveData(false)
    val showAssist: LiveData<Boolean> = _showAssist
    private val _showRebounds = MutableLiveData(false)
    val showRebounds: LiveData<Boolean> = _showRebounds
    private val _showBlocks = MutableLiveData(false)
    val showBlocks: LiveData<Boolean> = _showBlocks
    private val _showFaults = MutableLiveData(false)
    val showFaults: LiveData<Boolean> = _showFaults
    private val _showSteals = MutableLiveData(false)
    val showSteals: LiveData<Boolean> = _showSteals
    private val _showLosses = MutableLiveData(false)
    val showLosses: LiveData<Boolean> = _showLosses
    private val _showMinutes = MutableLiveData(false)
    val showMinutes: LiveData<Boolean> = _showMinutes

    //Variable para la carga de la screen
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    //Otras
    private val newMatchCourier = NewMatchCourier.getInstance()
    private val _matchStats = MutableLiveData(PlayerStatsClass())
    val matchStats: LiveData<PlayerStatsClass> = _matchStats

    init {
        viewModelScope.launch {
            _loading.value = true
            val player = playersVM.getById(playerId)
            _name.value = player.user.name
            _surname.value = player.user.surname1
            _surname2.value = player.user.surname2.toString()
            _loading.value = false
        }
    }

    fun insert(stats: PlayerStatsClass) {
        newMatchCourier.addPlayer(playerId, stats)
    }

    fun show(popupName: String) {
        when(popupName) {
            "Minutos" -> _showMinutes.value = true
            "Tiros de 2" -> _showShots2.value = true
            "Tiros de 3" -> _showShots3.value = true
            "Tiros libres" -> _showFShots.value = true
            "Asistencias" -> _showAssist.value = true
            "Rebotes" -> _showRebounds.value = true
            "Tapones" -> _showBlocks.value = true
            "Faltas" -> _showFaults.value = true
            "Robos" -> _showSteals.value = true
            "Pérdidas" -> _showLosses.value = true
        }
    }

    fun hide(popupName: String) {
        when(popupName) {
            "Minutos" -> _showMinutes.value = false
            "Tiros de 2" -> _showShots2.value = false
            "Tiros de 3" -> _showShots3.value = false
            "Tiros libres" -> _showFShots.value = false
            "Asistencias" -> _showAssist.value = false
            "Rebotes" -> _showRebounds.value = false
            "Tapones" -> _showBlocks.value = false
            "Faltas" -> _showFaults.value = false
            "Robos" -> _showSteals.value = false
            "Pérdidas" -> _showLosses.value = false
        }
    }
}

class AddPlayerScreenViewModelFactory(
    private val playersVM: PlayersViewModel,
    private val playerId: Int
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddPlayerScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddPlayerScreenViewModel(playersVM, playerId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}