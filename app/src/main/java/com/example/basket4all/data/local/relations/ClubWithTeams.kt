package com.example.basket4all.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.basket4all.data.local.entities.ClubEntity
import com.example.basket4all.data.local.entities.TeamEntity

/**
 * Definición de la relación 1:N entre [ClubEntity] y [TeamEntity]
 */
data class ClubWithTeams(
    @Embedded val club: ClubEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "Club"
    )
    val teams: List<TeamEntity>
)
