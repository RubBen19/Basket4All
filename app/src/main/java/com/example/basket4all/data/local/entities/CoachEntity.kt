package com.example.basket4all.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.basket4all.common.enums.CoachRoles

/**
 * Entidad que representa a los entrenadores en la base de datos local
 */
@Entity(tableName = "coaches_table")
data class CoachEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "Email") val email: String,
    @ColumnInfo(name = "Password") val password: String,
    @ColumnInfo(name = "Name") val name: String,
    @ColumnInfo(name = "First Surname") val surname1: String,
    @ColumnInfo(name = "Second Surname") val surname2: String?,
    @ColumnInfo(name = "Date of birth") val birthdate: String,
    @ColumnInfo(name = "Role") val coachrole: MutableList<CoachRoles> = mutableListOf()
)
