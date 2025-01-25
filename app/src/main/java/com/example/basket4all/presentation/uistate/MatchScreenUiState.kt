package com.example.basket4all.presentation.uistate

import com.example.basket4all.data.local.entities.PlayerEntity

data class MatchScreenUiState(
  // Marcador final y fecha
  val score: String = "0-0",
  val date: String = "Sin fecha",
  // Puntos marcados en cada cuarto
  val scoreQ1: String = "0-0",
  val scoreQ2: String = "0-0",
  val scoreQ3: String = "0-0",
  val scoreQ4: String = "0-0",
  // Premios individuales
  val maxPP: PlayerEntity? = null,
  val maxAP: PlayerEntity? = null,
  val maxRP: PlayerEntity? = null,
  val shooterP: PlayerEntity? = null,
  val maxSP: PlayerEntity? = null,
  // Flag de carga
  val loading: Boolean = false
)
