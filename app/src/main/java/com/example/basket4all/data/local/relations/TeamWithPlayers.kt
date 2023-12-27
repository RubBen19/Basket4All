package com.example.basket4all.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.basket4all.data.local.entities.PlayerEntity
import com.example.basket4all.data.local.entities.TeamEntity

/**
 * Definicion de la relación 1:N entre [TeamEntity] y [PlayerEntity]
 */
data class TeamWithPlayers(
    @Embedded val team: TeamEntity,
    @Relation(
        parentColumn = "teamId",
        entityColumn = "Team"
    )
    val players: List<PlayerEntity>
)
