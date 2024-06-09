package com.example.basket4all.presentation.viewmodels.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.basket4all.common.messengers.NewMatchCourier
import com.example.basket4all.common.messengers.SessionManager
import com.example.basket4all.data.local.entities.PlayerEntity
import com.example.basket4all.data.local.entities.TeamEntity
import com.example.basket4all.presentation.viewmodels.db.MatchesViewModel
import com.example.basket4all.presentation.viewmodels.db.PlayerStatsViewModel
import com.example.basket4all.presentation.viewmodels.db.TeamStatsViewModel
import com.example.basket4all.presentation.viewmodels.db.TeamViewModel
import kotlinx.coroutines.launch

class NewMatchScreenViewModel(
    private val teamVM: TeamViewModel,
    private val matchVM: MatchesViewModel,
    private val teamStatsVM: TeamStatsViewModel,
    private val playerStatsVM: PlayerStatsViewModel
): ViewModel() {
    //Otras variables
    private val session = SessionManager.getInstance()
    private val _courier = MutableLiveData(NewMatchCourier.getInstance())
    val courier: LiveData<NewMatchCourier> = _courier

    //Variables de la screen
    lateinit var vsTeams: List<TeamEntity>
    private lateinit var myTeam: TeamEntity
    private val _dropdownExpanded: MutableLiveData<Boolean> = MutableLiveData(false)
    val dropdownExpanded: LiveData<Boolean> = _dropdownExpanded
    private val _rival: MutableLiveData<String> = MutableLiveData("Selecciona rival")
    val rival: LiveData<String> = _rival
    private val _localScore: MutableLiveData<Int> = MutableLiveData(0)
    val localScore: LiveData<Int> = _localScore
    private val _visitorScore: MutableLiveData<Int> = MutableLiveData(0)
    val visitorScore: LiveData<Int> = _visitorScore
    private val _localOrVisitor: MutableLiveData<String> = MutableLiveData("LOCAL")
    val localOrVisitor: LiveData<String> = _localOrVisitor
    private val _players: MutableLiveData<List<PlayerEntity>> = MutableLiveData(listOf())
    val players: LiveData<List<PlayerEntity>> = _players
    private val _playersSelected: MutableLiveData<List<String>> = MutableLiveData(
        searchPlayersByID(_courier.value?.getPlayers())
            .map { "${it.user.name} ${it.user.surname1} ${it.user.surname2}" }
    )
    val playersSelected: LiveData<List<String>> = _playersSelected
    private val _playerSelectionShow: MutableLiveData<Boolean> = MutableLiveData(false)
    val playerSelectionShow: LiveData<Boolean> = _playerSelectionShow

    //Variable para la carga de la screen
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    init {
        viewModelScope.launch {
            _loading.value = true
            myTeam = teamVM.getById(session.getTeamId())
            searchOtherTeams()
            loadPlayers()
            _loading.value = false
        }
    }

    private fun searchPlayersByID(list: List<Int>?): List<PlayerEntity> {
        if (list != null) {
            return _players.value?.filter { list.contains(it.id) } ?: listOf()
        }
        return listOf()
    }

    private suspend fun loadPlayers() {
        _players.value = teamVM.getPlayers(myTeam.teamId)
    }

    private suspend fun searchOtherTeams() {
        vsTeams = teamVM.getByLigueAndCategory(myTeam.category, myTeam.league).minus(myTeam)
    }

    fun changeLocalOrVisitor() {
        if (_localOrVisitor.value == "LOCAL") _localOrVisitor.value = "VISITANTE"
        else _localOrVisitor.value = "LOCAL"
    }

    fun changeLocalScore(newScore: String) {
        _localScore.value = if (newScore == "") 0 else newScore.toInt()
    }

    fun changeVisitorScore(newScore: String) {
        _visitorScore.value = if (newScore == "") 0 else newScore.toInt()
    }

    fun changeDropdownExpanded() {
        _dropdownExpanded.value = !_dropdownExpanded.value!!
    }

    fun changeRival(newRival: String) {
        _rival.value = newRival
    }

    fun changePlayerSelectionShow() {
        _playerSelectionShow.value = !_playerSelectionShow.value!!
    }

    fun updateSelected() {
        _playersSelected.value = searchPlayersByID(_courier.value?.getPlayers())
            .map { "${it.user.name} ${it.user.surname1} ${it.user.surname2}" }
    }
}

class NewMatchScreenViewModelFactory(
    private val teamVM: TeamViewModel,
    private val matchVM: MatchesViewModel,
    private val teamStatsVM: TeamStatsViewModel,
    private val playerStatsVM: PlayerStatsViewModel
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewMatchScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewMatchScreenViewModel(teamVM, matchVM, teamStatsVM, playerStatsVM) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}