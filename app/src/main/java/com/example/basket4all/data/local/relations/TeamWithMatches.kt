package com.example.basket4all.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.basket4all.data.local.entities.MatchEntity
import com.example.basket4all.data.local.entities.TeamEntity

/**
 * Definición de la relación 1:N entre [TeamEntity] y [MatchEntity]
 */
data class TeamWithMatches(
    @Embedded val team: TeamEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "Local"
    )
    // Lista de partidos jugados como local
    val localMatches: List<MatchEntity>,
    @Relation(
    parentColumn = "id",
    entityColumn = "Visitor"
    )
    // Lista de partidos jugados como visitante
    val visitorMatches: List<MatchEntity>,
    // Lista de todos los partidos jugados, ya sea como local o visitante
    val matches: List<MatchEntity> = localMatches + visitorMatches
)
