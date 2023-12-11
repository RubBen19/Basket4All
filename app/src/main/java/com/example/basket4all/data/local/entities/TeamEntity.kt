package com.example.basket4all.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.basket4all.common.enums.Categories

/**
 * Entidad que representa a los equipos en la base de datos local
 */
@Entity(
    tableName = "teams_table",
    foreignKeys = [
        ForeignKey(
            entity = ClubEntity::class,
            parentColumns = ["id"],
            childColumns = ["Club"]
        )
    ]
)
data class TeamEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "Club")
    val clubId: Int,
    @ColumnInfo(name = "Name")
    val name: String,
    @ColumnInfo(name = "Category")
    val category: Categories,
    @ColumnInfo(name = "League")
    val league: String
)
