package com.example.basket4all.presentation.viewmodels.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.basket4all.common.messengers.SessionManager
import com.example.basket4all.data.local.entities.CalendarEventEntity
import com.example.basket4all.presentation.viewmodels.db.CalendarEventViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale
import kotlin.time.Duration.Companion.days

class CalendarScreenViewModel(
    private val calendarEventVM: CalendarEventViewModel
): ViewModel() {
    // Variables utilizadas en la pantalla de calendario
    private val actualDay = LocalDate.now()
    private val _month = MutableLiveData(actualDay.month)
    val month: LiveData<Month> = _month
    private val _year = MutableLiveData(actualDay.year)
    val year: LiveData<Int> = _year
    private val _showEventPopUp = MutableLiveData(false)
    val showEventPopUp: LiveData<Boolean> = _showEventPopUp
    private val _showNewEventPopUp = MutableLiveData(false)
    val showNewEventPopUp: LiveData<Boolean> = _showNewEventPopUp
    private val _showConfirm = MutableLiveData(false)
    val showConfirm: LiveData<Boolean> = _showConfirm
    private val _showDayEvents = MutableLiveData(false)
    val showDayEvents: LiveData<Boolean> = _showDayEvents

    //Variable para la carga de la screen
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    //Lista de eventos
    private val _eventsList = MutableLiveData<List<CalendarEventEntity>>(mutableListOf())
    val eventsList: LiveData<List<CalendarEventEntity>> = _eventsList
    //Lista de eventos diarios
    private val _dailyList = MutableLiveData<List<CalendarEventEntity>>(mutableListOf())
    val dailyList: LiveData<List<CalendarEventEntity>> = _dailyList

    //Recordar la fecha de un elemento clickable
    var date: LocalDate = LocalDate.now()

    //Recordar un evento a mostrar
    private val _event: MutableLiveData<CalendarEventEntity> = MutableLiveData()
    val event: LiveData<CalendarEventEntity> = _event

    init {
        load(800)
    }

    private fun load(timeMillis: Long = 0) {
        viewModelScope.launch {
            val teamId = SessionManager.getInstance().getTeamId()
            _loading.value = true
            _eventsList.value = calendarEventVM.getEvents(teamId).sortedBy { it.date }
            delay(timeMillis)
            _loading.value = false
        }
    }

    fun newEvent(event: CalendarEventEntity) {
        viewModelScope.launch {
            calendarEventVM.insert(event)
            load(200)
        }
    }

    fun removeEvent(event: CalendarEventEntity) {
        viewModelScope.launch {
            val e: CalendarEventEntity? = _eventsList.value?.first {
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
        Log.d("DATE", "Before: ${_month.value}")
        if (_month.value == Month.DECEMBER) {
            _month.value = Month.JANUARY
            _year.value = _year.value?.plus(1)
        }
        else _month.value = Month.of(_month.value?.value?.plus(1)!!)
        Log.d("DATE", "After: ${_month.value}")
    }

    fun backMonth() {
        Log.d("DATE", "Before: ${_month.value}")
        if (_month.value == Month.JANUARY) {
            _month.value = Month.DECEMBER
            _year.value = _year.value?.minus(1)
        }
        else _month.value = Month.of(_month.value?.value?.minus(1)!!)
        Log.d("DATE", "After: ${_month.value}")
    }

    fun showAddPopUp(date: LocalDate) {
        this.date = date
        _showNewEventPopUp.value = true
    }

    fun hideAddPopUp() {
        _showNewEventPopUp.value = false
    }

    fun showEvent(event: CalendarEventEntity) {
        _event.value = event
        _showEventPopUp.value = true
    }

    fun hideEvent() {
        _showEventPopUp.value = false
    }

    fun showConfirmWindow(event: CalendarEventEntity) {
        _event.value = event
        _showConfirm.value = true
    }

    fun hideConfirmWindow() {
        _showConfirm.value = false
    }

    fun loadDailyEvents() {
        viewModelScope.launch {
            val teamId = SessionManager.getInstance().getTeamId()
            _dailyList.value = calendarEventVM.getEvents(teamId, date)
            delay(500)
        }
    }

    fun showDayEvents(date: LocalDate) {
        this.date = date
        _showDayEvents.value = true
    }

    fun hideDayEvents() {
        _showDayEvents.value = false
    }
}

class CalendarScreenViewModelFactory(
    private val calendarEventVM: CalendarEventViewModel
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalendarScreenViewModel(calendarEventVM) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}