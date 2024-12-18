package com.example.basket4all.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.basket4all.common.classes.PlayerClass
import com.example.basket4all.common.elements.categoryAssigner
import com.example.basket4all.common.enums.PlayerPositions
import com.example.basket4all.common.enums.Categories

/**
 * Entidad que representa a los jugadores en la base de datos local
 */
@Entity(tableName = "players_table")
data class PlayerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @Embedded
    val user: User,
    @ColumnInfo(name = "Team")
    val teamId: Int,
    @ColumnInfo(name = "Positions")
    val positions: MutableList<PlayerPositions> = mutableListOf(),
    @ColumnInfo(name = "Category")
    val categories: Categories = categoryAssigner(user.birthdate),
    @ColumnInfo(name = "Number")
    var number: Int = (-1)
) {
    fun getPositionsName(): List<String> {
        val list = mutableListOf<String>()
        this.positions.forEach { list.add(it.name) }
        return list
    }
}
