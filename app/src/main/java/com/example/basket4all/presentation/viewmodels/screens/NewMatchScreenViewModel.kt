package com.example.basket4all.presentation.viewmodels.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.basket4all.common.classes.MatchScore
import com.example.basket4all.common.classes.Score
import com.example.basket4all.common.messengers.NewMatchCourier
import com.example.basket4all.common.messengers.SessionManager
import com.example.basket4all.data.local.entities.MatchEntity
import com.example.basket4all.data.local.entities.MatchStats
import com.example.basket4all.data.local.entities.PlayerEntity
import com.example.basket4all.data.local.entities.TeamEntity
import com.example.basket4all.presentation.viewmodels.db.MatchStatsViewModel
import com.example.basket4all.presentation.viewmodels.db.MatchesViewModel
import com.example.basket4all.presentation.viewmodels.db.PlayerStatsViewModel
import com.example.basket4all.presentation.viewmodels.db.TeamStatsViewModel
import com.example.basket4all.presentation.viewmodels.db.TeamViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

class NewMatchScreenViewModel(
    private val teamVM: TeamViewModel,
    private val matchVM: MatchesViewModel,
    private val teamStatsVM: TeamStatsViewModel,
    private val playerStatsVM: PlayerStatsViewModel,
    private val matchStatsVM: MatchStatsViewModel
): ViewModel() {
    //Otras variables
    private val session = SessionManager.getInstance()
    private val _courier = MutableLiveData(NewMatchCourier.getInstance())

    //Variables de la screen
    lateinit var vsTeams: List<TeamEntity>
    private lateinit var myTeam: TeamEntity
    private val _dropdownExpanded: MutableLiveData<Boolean> = MutableLiveData(false)
    val dropdownExpanded: LiveData<Boolean> = _dropdownExpanded
    private val _rival: MutableLiveData<String> = MutableLiveData("Selecciona rival")
    val rival: LiveData<String> = _rival
    // Marcador local
    private val _localScoreQ1: MutableLiveData<Int> = MutableLiveData(0)
    val localScoreQ1: LiveData<Int> = _localScoreQ1
    private val _localScoreQ2: MutableLiveData<Int> = MutableLiveData(0)
    val localScoreQ2: LiveData<Int> = _localScoreQ2
    private val _localScoreQ3: MutableLiveData<Int> = MutableLiveData(0)
    val localScoreQ3: LiveData<Int> = _localScoreQ3
    private val _localScoreQ4: MutableLiveData<Int> = MutableLiveData(0)
    val localScoreQ4: LiveData<Int> = _localScoreQ4
    // Marcador visitante
    private val _visitorScoreQ1: MutableLiveData<Int> = MutableLiveData(0)
    val visitorScoreQ1: LiveData<Int> = _visitorScoreQ1
    private val _visitorScoreQ2: MutableLiveData<Int> = MutableLiveData(0)
    val visitorScoreQ2: LiveData<Int> = _visitorScoreQ2
    private val _visitorScoreQ3: MutableLiveData<Int> = MutableLiveData(0)
    val visitorScoreQ3: LiveData<Int> = _visitorScoreQ3
    private val _visitorScoreQ4: MutableLiveData<Int> = MutableLiveData(0)
    val visitorScoreQ4: LiveData<Int> = _visitorScoreQ4
    // Seleccion de equipo local o visitante
    private val _localOrVisitor: MutableLiveData<String> = MutableLiveData("LOCAL")
    val localOrVisitor: LiveData<String> = _localOrVisitor
    // Lista de jugadores
    private val _players: MutableLiveData<List<PlayerEntity>> = MutableLiveData(listOf())
    val players: LiveData<List<PlayerEntity>> = _players
    private val _playersSelected: MutableLiveData<List<String>> = MutableLiveData(
        searchPlayersByID(_courier.value?.getPlayers())
            .map { "${it.user.name} ${it.user.surname1} ${it.user.surname2}" }
    )
    val playersSelected: LiveData<List<String>> = _playersSelected
    private val _playerSelectionShow: MutableLiveData<Boolean> = MutableLiveData(false)
    val playerSelectionShow: LiveData<Boolean> = _playerSelectionShow
    private val _date: MutableLiveData<LocalDate> = MutableLiveData()
    val date: LiveData<LocalDate> = _date

    //Variable para la carga de la screen
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    init {
        viewModelScope.launch {
            _loading.value = true
            myTeam = teamVM.getById(session.getTeamId())
            searchOtherTeams()
            loadPlayers()
            delay(800)
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

    fun changeLocalScore(newScore: String, q: Int) {
        when (q) {
            1 -> _localScoreQ1.value = if (newScore == "") 0 else newScore.toInt()
            2 -> _localScoreQ2.value = if (newScore == "") 0 else newScore.toInt()
            3 -> _localScoreQ3.value = if (newScore == "") 0 else newScore.toInt()
            4 -> _localScoreQ4.value = if (newScore == "") 0 else newScore.toInt()
        }
    }

    fun changeVisitorScore(newScore: String, q: Int) {
        when (q) {
            1 -> _visitorScoreQ1.value = if (newScore == "") 0 else newScore.toInt()
            2 -> _visitorScoreQ2.value = if (newScore == "") 0 else newScore.toInt()
            3 -> _visitorScoreQ3.value = if (newScore == "") 0 else newScore.toInt()
            4 -> _visitorScoreQ4.value = if (newScore == "") 0 else newScore.toInt()
        }
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

    fun changeDate(day: Int, month: Int, year: Int) {
        _date.value = LocalDate.of(year, month, day)
    }

    fun saveChanges() {
        val teamId = vsTeams.find { it.name == _rival.value }?.teamId
        val playerLists = _courier.value?.getPlayers()
        viewModelScope.launch {
            if(teamId != null && _date.value != null && ((playerLists?.size ?: 0) >= 5)) {
                val matchScore = MatchScore(
                    scoreQ1 = Score(_localScoreQ1.value?:0, _visitorScoreQ1.value?:0),
                    scoreQ2 = Score(_localScoreQ2.value?:0, _visitorScoreQ2.value?:0),
                    scoreQ3 = Score(_localScoreQ3.value?:0, _visitorScoreQ3.value?:0),
                    scoreQ4 = Score(_localScoreQ4.value?:0, _visitorScoreQ4.value?:0)
                )
                val existMatch = matchVM.searchMatch(myTeam.teamId, teamId, _date.value!!)
                if (existMatch != null) {
                    // Update the match
                    existMatch.score = matchScore
                    matchVM.update(existMatch)
                }
                else {
                    // Save the match
                    val match = MatchEntity(
                        localTeamId = myTeam.teamId,
                        visitorTeamId = teamId,
                        date = _date.value!!,
                        score = matchScore
                    )
                    matchVM.insertMatch(match)
                    // Save team stats
                    val teamStats = teamStatsVM.getByTeamId(session.getTeamId())
                    teamStats.matchPlayed += 1
                    if (_localOrVisitor.value == "LOCAL" &&
                        matchScore.getLocalScore() > matchScore.getVisitorScore()
                    ) {
                        teamStats.wins += 1
                    }
                    else if (_localOrVisitor.value == "VISITANTE" &&
                        matchScore.getLocalScore() < matchScore.getVisitorScore()
                    ) {
                        teamStats.wins += 1
                    }
                    // Save players and his stats
                    _courier.value?.getStats()?.forEach { player ->
                        val matchId = matchVM.searchEqualMatch(
                            match.localTeamId,
                            match.visitorTeamId,
                            match.date,
                            match.score
                        )?.id
                        if (matchId != null) {
                            val matchStats = MatchStats(
                                matchId = matchId,
                                playerId = player.id,
                                stats = player
                            )
                            matchStatsVM.insert(matchStats)
                            playerStatsVM.updateStats(
                                player.id,
                                matchStatsVM.playerMatchStats(player.id)
                            )
                            teamStats.points += player.shots.getPoints()
                            teamStats.fouls += player.faults.getTotal()
                            teamStats.rebounds += player.rebounds.getTotal()
                            teamStats.turnovers += player.losts
                            teamStatsVM.update(teamStats)
                        }
                    }
                }
            }
        }
    }
}

class NewMatchScreenViewModelFactory(
    private val teamVM: TeamViewModel,
    private val matchVM: MatchesViewModel,
    private val teamStatsVM: TeamStatsViewModel,
    private val playerStatsVM: PlayerStatsViewModel,
    private val matchStatsViewModel: MatchStatsViewModel
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewMatchScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewMatchScreenViewModel(
                teamVM, matchVM, teamStatsVM, playerStatsVM, matchStatsViewModel
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}