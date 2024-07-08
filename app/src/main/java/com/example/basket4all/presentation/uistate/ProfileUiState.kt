package com.example.basket4all.presentation.uistate

import com.example.basket4all.data.local.entities.PlayerStats
import com.example.basket4all.data.local.entities.TeamEntity

data class ProfileUiState(
    val username: String = "",
    val surname: String = "",
    val number: Int = 0,
    val positions: List<String> = listOf(),
    val team: TeamEntity? = null,
    val stats: PlayerStats? = null,
    val loading: Boolean = false
)
