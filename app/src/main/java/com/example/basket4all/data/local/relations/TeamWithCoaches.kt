package com.example.basket4all.data.local.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.basket4all.data.local.entities.CoachEntity
import com.example.basket4all.data.local.entities.CoachTeamCrossRef
import com.example.basket4all.data.local.entities.TeamEntity

data class TeamWithCoaches(
    @Embedded val team: TeamEntity,
    @Relation(
        parentColumn = "teamId",
        entityColumn = "coachId",
        associateBy = Junction(CoachTeamCrossRef::class)
    )
    val coaches: List<CoachEntity>
)