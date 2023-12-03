package com.example.basket4all.data.local.entities

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.basket4all.common.elements.categoryAssigner
import com.example.basket4all.common.enums.PlayerPositions
import com.example.basket4all.common.enums.Categories
import java.time.LocalDate

/**
 * Entidad que representa a los jugadores en la base de datos local
 */
@Entity(tableName = "players_table")
data class PlayerEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "Email") val email: String,
    @ColumnInfo(name = "Password") val password: String,
    @ColumnInfo(name = "Name") val name: String,
    @ColumnInfo(name = "First Surname") val surname1: String,
    @ColumnInfo(name = "Second Surname") val surname2: String?,
    @ColumnInfo(name = "Date of birth") val birthdate: LocalDate,
    @ColumnInfo(name = "Positions") val positions: MutableList<PlayerPositions> = mutableListOf(),
    @ColumnInfo(name = "Category") val categories: Categories = categoryAssigner(birthdate)
)
