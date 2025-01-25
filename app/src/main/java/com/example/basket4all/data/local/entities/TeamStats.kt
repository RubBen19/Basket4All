package com.example.basket4all.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "teams_stats_table",)
data class TeamStats(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "Team")
    val teamId: Int,
    @ColumnInfo(name = "Partidos")
    var matchPlayed: Int = 0,
    @ColumnInfo(name = "Victorias")
    var wins: Int = 0,
    @ColumnInfo(name = "Puntos")
    var points: Int = 0,
    @ColumnInfo(name = "Faltas")
    var fouls: Int = 0,
    @ColumnInfo(name = "Pérdidas")
    var turnovers: Int = 0,
    @ColumnInfo(name = "Rebotes")
    var rebounds: Int = 0,
)
