package com.example.basket4all.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import com.example.basket4all.common.enums.CoachRoles

@Entity(
    tableName = "coach_team_table",
    primaryKeys = ["coachId", "teamId"],
    indices = [Index("teamId")])
data class CoachTeamCrossRef(
    val coachId: Int,
    val teamId: Int,
    val rol: CoachRoles
)
