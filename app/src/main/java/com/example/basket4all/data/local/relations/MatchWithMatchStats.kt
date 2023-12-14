package com.example.basket4all.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.basket4all.data.local.entities.MatchEntity
import com.example.basket4all.data.local.entities.MatchStats

/**
 * Definición de la relación 1:N entre [MatchEntity] y [MatchStats]
 */
data class MatchWithMatchStats(
    @Embedded val match: MatchEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "Match"
    )
    val matchesStats: List<MatchStats>
)
