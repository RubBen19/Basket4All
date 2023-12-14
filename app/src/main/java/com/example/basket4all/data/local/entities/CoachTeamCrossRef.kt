package com.example.basket4all.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "coach_team_table",
    primaryKeys = ["coachId", "teamId"])
data class CoachTeamCrossRef(
    @ColumnInfo("Coach")
    val coachId: Int,
    @ColumnInfo("Team")
    val teamId: Int
)
