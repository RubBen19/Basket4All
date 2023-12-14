package com.example.basket4all.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.basket4all.data.local.entities.PlayerEntity
import com.example.basket4all.data.local.entities.PlayerStats

/**
 * Definición de la relación 1:1 entre [PlayerEntity] y [PlayerStats]
 */
data class PlayerAndPlayerStats(
    @Embedded val player: PlayerEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "Player"
    )
    val stats: PlayerStats
)
