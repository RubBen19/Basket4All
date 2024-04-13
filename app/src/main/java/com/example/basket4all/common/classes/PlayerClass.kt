package com.example.basket4all.common.classes

import com.example.basket4all.common.enums.Categories
import com.example.basket4all.common.enums.PlayerPositions
import java.time.LocalDate

class PlayerClass(
    id: Int,
    email: String,
    name: String,
    surname1: String,
    surname2: String?,
    birthdate: LocalDate,
    picture: Int?,
    private val teamId: Int,
    private val positions: List<PlayerPositions>,
    private val categories: Categories
): UserClass(id, email, name, surname1, surname2, birthdate, picture) {

    fun getTeamId(): Int {
        return teamId
    }

    fun getPositions(): List<PlayerPositions> {
        return positions
    }

    fun getCategories(): Categories {
        return categories
    }

}