package com.example.basket4all.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "players_stats_table",
    foreignKeys = [
        ForeignKey(
            entity = PlayerEntity::class,
            parentColumns = ["id"],
            childColumns = ["Player"]
        )
    ]
)
data class PlayerStats(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "Player")
    val playerId: Int
)