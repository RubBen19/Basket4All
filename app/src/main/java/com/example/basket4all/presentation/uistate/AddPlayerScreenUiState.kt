package com.example.basket4all.presentation.uistate

import com.example.basket4all.common.classes.PlayerStatsClass
import com.example.basket4all.common.messengers.NewMatchCourier

data class AddPlayerScreenUiState(
    // Variables de la screen
    val name: String = "",
    val surname: String = "",
    val surname2: String = "",

    // Variables para los pop-up
    val showShots2: Boolean = false,
    val showShots3: Boolean = false,
    val showFShots: Boolean = false,
    val showAssist: Boolean = false,
    val showBlocks: Boolean = false,
    val showFaults: Boolean = false,
    val showSteals: Boolean = false,
    val showLosses: Boolean = false,
    val showMinutes: Boolean = false,
    val showRebounds: Boolean = false,

    // Variables para la carga de la screen
    val loading:Boolean = false,

    // Otras
    val newMatchCourier: NewMatchCourier = NewMatchCourier.getInstance(),
    val matchStats: PlayerStatsClass = PlayerStatsClass()
)
