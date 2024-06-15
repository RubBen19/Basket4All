package com.example.basket4all.presentation.viewmodels.db

import android.app.usage.UsageEvents.Event
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.basket4all.data.local.daos.CalendarEventDao
import com.example.basket4all.data.local.entities.CalendarEventEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarEventViewModel(private val calendarEventDao: CalendarEventDao): ViewModel() {

    fun insert(event: CalendarEventEntity) {
        viewModelScope.launch {
            calendarEventDao.insert(event)
        }
    }

    fun delete(event: CalendarEventEntity) {
        viewModelScope.launch {
            calendarEventDao.delete(event)
        }
    }

    fun update(event: CalendarEventEntity) {
        viewModelScope.launch {
            calendarEventDao.update(event)
        }
    }

    suspend fun getEvents(teamId: Int): List<CalendarEventEntity> {
        return calendarEventDao.getByTeam(teamId).first()
    }

    suspend fun getEvents(teamId: Int, date: LocalDate): List<CalendarEventEntity> {
        return calendarEventDao.getByDateTeam(teamId, date).first()
    }
}

class CalendarEventViewModelFactory(private val calendarEventDao: CalendarEventDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarEventViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalendarEventViewModel(calendarEventDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}