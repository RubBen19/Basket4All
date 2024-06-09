package com.example.basket4all.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.basket4all.common.classes.CoachClass
import com.example.basket4all.common.enums.CoachRoles

/**
 * Entidad que representa a los entrenadores en la base de datos local
 */
@Entity(tableName = "coaches_table")
data class CoachEntity(
    @PrimaryKey(autoGenerate = true)
    val coachId: Int = 0,
    @Embedded
    val user: User,
    @ColumnInfo(name = "Roles")
    val coachroles: MutableList<CoachRoles> = mutableListOf()
) {
    fun toCoachClass(): CoachClass {
        return CoachClass(coachId, user.email, user.name, user.surname1, user.surname2,
            user.birthdate, user.picture, coachroles)
    }

    fun getCoachRoles(): List<String> {
        val list = mutableListOf<String>()
        this.coachroles.forEach { list.add(it.name) }
        return list
    }
}
