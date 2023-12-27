package com.example.basket4all.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.basket4all.data.local.entities.TeamEntity
import com.example.basket4all.data.local.entities.TeamStats

/**
 * Definición de la relación 1:1 entre [TeamEntity] y [TeamStats]
 */
data class TeamAndTeamStats(
    @Embedded val team: TeamEntity,
    @Relation(
        parentColumn = "teamId",
        entityColumn = "Team"
    )
    val stats: TeamStats
)
