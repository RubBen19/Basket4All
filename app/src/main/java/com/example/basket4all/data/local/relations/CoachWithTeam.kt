package com.example.basket4all.data.local.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.basket4all.data.local.entities.CoachEntity
import com.example.basket4all.data.local.entities.CoachTeamCrossRef
import com.example.basket4all.data.local.entities.TeamEntity

/**
 * Definiciónes de la relacion N:M entre [CoachEntity] y [TeamEntity]
 */

data class CoachWithTeam(
    @Embedded val coach: CoachEntity,
    @Relation(
        parentColumn = "coachId",
        entityColumn = "teamId",
        associateBy = Junction(CoachTeamCrossRef::class)
    )
    val teams: List<TeamEntity>
)
