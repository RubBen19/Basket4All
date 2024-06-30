package com.example.basket4all.presentation.viewmodels.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.basket4all.common.enums.EventType
import com.example.basket4all.common.messengers.SessionManager
import com.example.basket4all.data.local.entities.CalendarEventEntity
import com.example.basket4all.data.local.entities.MatchEntity
import com.example.basket4all.data.local.entities.TeamEntity
import com.example.basket4all.presentation.uistate.HomeScreenUiState
import com.example.basket4all.presentation.viewmodels.db.CalendarEventViewModel
import com.example.basket4all.presentation.viewmodels.db.CoachesViewModel
import com.example.basket4all.presentation.viewmodels.db.MatchesViewModel
import com.example.basket4all.presentation.viewmodels.db.PlayersViewModel
import com.example.basket4all.presentation.viewmodels.db.TeamViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class HomeScreenViewModel(
    private val playerVM: PlayersViewModel,
    private val coachesVM: CoachesViewModel,
    private val eventsVM: CalendarEventViewModel,
    private val matchesVM: MatchesViewModel,
    private val teamVM: TeamViewModel
): ViewModel() {
    // Screen UI State
    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    fun load() {
        viewModelScope.launch {
            loadUsername()
            loadEvents()
            loadMatches()
            loadLastMatch()
        }
    }

    private suspend fun loadUsername() {
        val sessionManager = SessionManager.getInstance()
        val name: String = when(sessionManager.getRole()) {
            true -> playerVM.getById(sessionManager.getUserId()).user.name
            false -> coachesVM.getById(sessionManager.getUserId()).user.name
            else -> error("ERROR: Usuario no es Jugador ni Entrenador")
        }
        _uiState.update { it.copy(username = name) }
    }

    private suspend fun loadEvents() {
        Log.d("EVENTS_DB", "INICIO: Carga de eventos desde Home Screen")
        val sessionManager = SessionManager.getInstance()
        val events = eventsVM.getEvents(sessionManager.getTeamId()).filter {
            it.date >= LocalDate.now() && it.type != EventType.MATCH
        }
        if (events.size >= 5) {
            _uiState.update { uiState ->
                uiState.copy(
                    events = events.sortedBy { it.date }.subList(0, 5)
                )
            }
        }
        else _uiState.update { uiState -> uiState.copy(events = events.sortedBy { it.date }) }
        Log.d("EVENTS_DB", "FIN: Carga de eventos desde Home Screen")
    }

    private suspend fun loadMatches() {
        Log.d("EVENTS_DB", "INICIO: Carga de partidos desde Home Screen")
        val sessionManager = SessionManager.getInstance()
        val events = eventsVM.getEvents(sessionManager.getTeamId()).filter {
            it.date >= LocalDate.now() && it.type == EventType.MATCH
        }
        if (events.size >= 5) {
            _uiState.update { uiState ->
                uiState.copy(
                    matches = events.sortedBy { it.date }.subList(0, 5)
                )
            }
        }
        else _uiState.update { uiState -> uiState.copy(matches = events.sortedBy { it.date }) }
        Log.d("EVENTS_DB", "FIN: Carga de partidos desde Home Screen")
    }

    private suspend fun loadLastMatch() {
        Log.d("MATCHES_DB", "INICIO: Carga del último partido jugado desde Home Screen")
        val userTeamId = SessionManager.getInstance().getTeamId()
        val match = matchesVM.getMatchesByTeamId(userTeamId)
            .filter { it.localTeamId == userTeamId || it.visitorTeamId == userTeamId }
            .maxByOrNull { it.date }
        if (match != null) {
            _uiState.update {ui ->
                ui.copy(
                    lastMatch = match,
                    localTeam = teamVM.getById(match.localTeamId),
                    visitorTeam = teamVM.getById(match.visitorTeamId)
                )
            }
        }
        Log.d("MATCHES_DB", "FIN: Carga del último partido jugado desde Home Screen")
    }
}

class HomeScreenViewModelFactory(
    private val playerVM: PlayersViewModel,
    private val coachesVM: CoachesViewModel,
    private val eventsVM: CalendarEventViewModel,
    private val matchesVM: MatchesViewModel,
    private val teamVM: TeamViewModel
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeScreenViewModel(playerVM, coachesVM, eventsVM, matchesVM, teamVM) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}