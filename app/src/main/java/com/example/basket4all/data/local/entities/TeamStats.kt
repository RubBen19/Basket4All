package com.example.basket4all.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "teams_stats_table",)
data class TeamStats(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "Team")
    val teamId: Int
)
