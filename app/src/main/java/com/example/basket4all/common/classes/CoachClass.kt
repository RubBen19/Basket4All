package com.example.basket4all.common.classes

import com.example.basket4all.common.enums.CoachRoles
import java.time.LocalDate

class CoachClass(
    id: Int,
    email: String,
    name: String,
    surname1: String,
    surname2: String?,
    birthdate: LocalDate,
    picture: Int?,
    private val coachRoles: List<CoachRoles>
): UserClass(id, email, name, surname1, surname2, birthdate, picture) {

    fun getCoachRoles(): List<CoachRoles> {
        return coachRoles
    }
}