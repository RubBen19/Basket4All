package com.example.basket4all.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "matches_stats_table")
data class MatchStats(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo("Match")
    val matchId: Int,
    @ColumnInfo("Player")
    val playerId: Int
)
