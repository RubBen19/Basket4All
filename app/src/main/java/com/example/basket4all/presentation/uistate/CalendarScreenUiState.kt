package com.example.basket4all.presentation.uistate

import com.example.basket4all.data.local.entities.CalendarEventEntity
import java.time.LocalDate
import java.time.Month

data class CalendarScreenUiState(
    // Variables utilizadas en la pantalla de calendario
    val month: Month = LocalDate.now().month,
    val year: Int = LocalDate.now().year,
    val showEventPopUp: Boolean = false,
    val showNewEventPopUp: Boolean = false,
    val showConfirm: Boolean = false,
    val showDayEvents: Boolean = false,

    //Variables para seleccionar rival en eventos de partidos
    val dropdownExpanded: Boolean = false,

    //Variable para la carga de la screen
    val loading: Boolean = false,

    //Lista de eventos
    val eventsList: List<CalendarEventEntity> = listOf(),

    //Lista de eventos diarios
    val dailyList: List<CalendarEventEntity> = listOf(),

    //Recordar un evento a mostrar
    val event: CalendarEventEntity? = null
)
