package com.example.basket4all.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.basket4all.common.classes.PlayerStatsClass

@Entity(tableName = "players_stats_table")
data class PlayerStats(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "Player")
    val playerId: Int,
    @ColumnInfo(name = "Estadísticas")
    val stats: PlayerStatsClass = PlayerStatsClass()
)
