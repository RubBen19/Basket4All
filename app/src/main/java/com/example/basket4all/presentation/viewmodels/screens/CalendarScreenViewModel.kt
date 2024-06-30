package com.example.basket4all.presentation.viewmodels.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.basket4all.common.messengers.SessionManager
import com.example.basket4all.data.local.entities.CalendarEventEntity
import com.example.basket4all.data.local.entities.TeamEntity
import com.example.basket4all.presentation.uistate.CalendarScreenUiState
import com.example.basket4all.presentation.viewmodels.db.CalendarEventViewModel
import com.example.basket4all.presentation.viewmodels.db.TeamViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month

class CalendarScreenViewModel(
    private val calendarEventVM: CalendarEventViewModel,
    private val teamVM: TeamViewModel
): ViewModel() {
    // Screen UI state
    private val _uiState = MutableStateFlow(CalendarScreenUiState())
    val uiState: StateFlow<CalendarScreenUiState> = _uiState.asStateFlow()

    //Variables para seleccionar rival en eventos de partidos
    private val session = SessionManager.getInstance()
    private lateinit var myTeam: TeamEntity
    lateinit var vsTeams: List<TeamEntity>

    //Recordar la fecha de un elemento clickable
    var date: LocalDate = LocalDate.now()

    init {
        load(800)
    }

    private fun load(timeMillis: Long = 0) {
        viewModelScope.launch {
            val teamId = SessionManager.getInstance().getTeamId()
            _uiState.update { ui ->
                ui.copy(
                    loading = true,
                    eventsList = calendarEventVM.getEvents(teamId).sortedBy { it.date },
                )
            }
            myTeam = teamVM.getById(session.getTeamId())
            searchOtherTeams()
            delay(timeMillis)
            _uiState.update { it.copy(loading = false) }
        }
    }

    private suspend fun searchOtherTeams() {
        vsTeams = teamVM.getByLigueAndCategory(myTeam.category, myTeam.league).minus(myTeam)
    }

    fun newEvent(event: CalendarEventEntity) {
        viewModelScope.launch {
            calendarEventVM.insert(event)
            load(200)
        }
    }

    fun removeEvent(event: CalendarEventEntity) {
        viewModelScope.launch {
            val e: CalendarEventEntity? = _uiState.value.eventsList.firstOrNull {
                it.date == event.date &&
                it.hour == event.hour &&
                it.teamId == event.teamId &&
                it.description == event.description &&
                it.type == event.type &&
                it.place == event.place
            }
            if (e != null) {
                calendarEventVM.delete(event)
                load(200)
            }
        }
    }

    fun nextMonth() {
        Log.d("DATE", "Before: ${_uiState.value.month}")
        if (_uiState.value.month == Month.DECEMBER) {
            _uiState.update {
                it.copy(
                    month = Month.JANUARY,
                    year = it.year.plus(1)
                )
            }
        }
        else _uiState.update { it.copy(month = Month.of(_uiState.value.month.value.plus(1))) }
        Log.d("DATE", "After: ${_uiState.value.month}")
    }

    fun backMonth() {
        Log.d("DATE", "Before: ${_uiState.value.month}")
        if (_uiState.value.month == Month.JANUARY) {
            _uiState.update {
                it.copy(
                    month = Month.DECEMBER,
                    year = it.year.minus(1)
                )
            }
        }
        else _uiState.update { it.copy(month = Month.of(_uiState.value.month.value.minus(1))) }
        Log.d("DATE", "After: ${_uiState.value.month}")
    }

    fun showAddPopUp(date: LocalDate) {
        this.date = date
        _uiState.update { it.copy(showNewEventPopUp = true) }

    }

    fun hideAddPopUp() {
        _uiState.update { it.copy(showNewEventPopUp = false) }
    }

    fun showEvent(event: CalendarEventEntity) {
        _uiState.update {
            it.copy(
                event = event,
                showEventPopUp = true
            )
        }
    }

    fun hideEvent() {
        _uiState.update { it.copy(showEventPopUp = false) }
    }

    fun showConfirmWindow(event: CalendarEventEntity) {
        _uiState.update {
            it.copy(
                event = event,
                showConfirm = true
            )
        }
    }

    fun hideConfirmWindow() {
        _uiState.update { it.copy(showConfirm = false) }
    }

    fun loadDailyEvents() {
        viewModelScope.launch {
            val teamId = SessionManager.getInstance().getTeamId()
            _uiState.update { it.copy(dailyList = calendarEventVM.getEvents(teamId, date)) }
            delay(500)
        }
    }

    fun showDayEvents(date: LocalDate) {
        this.date = date
        _uiState.update { it.copy(showDayEvents = true) }
    }

    fun hideDayEvents() {
        _uiState.update { it.copy(showDayEvents = false) }
    }

    fun changeDropdownExpanded() {
        _uiState.update { it.copy(dropdownExpanded = !_uiState.value.dropdownExpanded) }
    }
}

class CalendarScreenViewModelFactory(
    private val calendarEventVM: CalendarEventViewModel,
    private val teamVM: TeamViewModel
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalendarScreenViewModel(calendarEventVM, teamVM) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}