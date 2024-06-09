package com.example.basket4all.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.basket4all.common.classes.PlayerStatsClass

@Entity(tableName = "matches_stats_table")
data class MatchStats(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("Match")
    val matchId: Int,
    @ColumnInfo("Player")
    val playerId: Int,
    @ColumnInfo
    val stats: PlayerStatsClass
)
