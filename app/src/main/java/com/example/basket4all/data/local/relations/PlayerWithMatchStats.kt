package com.example.basket4all.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.basket4all.data.local.entities.MatchStats
import com.example.basket4all.data.local.entities.PlayerEntity

/**
 * Definición de la relación 1:N entre [PlayerEntity] y [MatchStats]
 */
data class PlayerWithMatchStats(
    @Embedded val player: PlayerEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "Player"
    )
    val matchStats: List<MatchStats>
)
