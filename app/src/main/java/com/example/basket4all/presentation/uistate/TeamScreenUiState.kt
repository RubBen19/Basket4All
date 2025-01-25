package com.example.basket4all.presentation.uistate

import com.example.basket4all.R
import com.example.basket4all.data.local.entities.PlayerEntity
import com.example.basket4all.data.local.entities.TeamEntity

data class TeamScreenUiState(
    val team: TeamEntity? = null,
    val teamLogo: Int = R.drawable.logo_default,
    val players: List<PlayerEntity> = listOf(),
    val defeats: Int = 0,
    val wins: Int = 0,
    val points: Double = 0.0,
    val loading: Boolean = false
)
