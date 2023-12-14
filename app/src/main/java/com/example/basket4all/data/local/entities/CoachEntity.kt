package com.example.basket4all.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.basket4all.common.enums.CoachRoles

/**
 * Entidad que representa a los entrenadores en la base de datos local
 */
@Entity(
    tableName = "coaches_table",
    foreignKeys = [
        ForeignKey(
            entity = TeamEntity::class,
            parentColumns = ["id"],
            childColumns = ["Team"]
        )
    ],
    indices = [Index("Team")]
)
data class CoachEntity(
    @PrimaryKey(autoGenerate = true)
    val coachId: Int = 0,
    @Embedded
    val user: UserEntity,
    @ColumnInfo(name = "Team")
    val teamId: List<Int>,
    /*
    @ColumnInfo(name = "Roles")
    val coachroles: MutableList<CoachRoles> = mutableListOf()
     */
)
