package com.example.basket4all.presentation.uistate

import com.example.basket4all.R
import com.example.basket4all.data.local.entities.CalendarEventEntity
import com.example.basket4all.data.local.entities.MatchEntity
import com.example.basket4all.data.local.entities.TeamEntity

data class HomeScreenUiState(
    // Variables para mostrar en la screen
    val username: String = "",
    val teamLogo: Int = R.drawable.logo_default,
    val events: List<CalendarEventEntity> = listOf(),
    val matches: List<CalendarEventEntity> = listOf(),
    val lastMatch: MatchEntity? = null,

    // Variable para la carga de información desde la screen
    val loading: Boolean = false,

    // Variables para mostrar los equipos de un último partido
    val localTeam: TeamEntity? = null,
    val visitorTeam: TeamEntity? = null
)
