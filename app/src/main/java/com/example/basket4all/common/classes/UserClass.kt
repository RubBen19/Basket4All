package com.example.basket4all.common.classes

import java.time.LocalDate

abstract class UserClass(
    private val id: Int,
    private val email: String,
    private val name: String,
    private val surname1: String,
    private val surname2: String?,
    private val birthdate: LocalDate,
    private val picture: Int?
) {
    fun getId(): Int {
        return id
    }

    fun getEmail(): String {
        return email
    }

    fun getName(): String {
        return name
    }

    fun getFirstSurname(): String {
        return surname1
    }

    fun getSecondSurname(): String? {
        return surname2
    }

    fun getBirthdate(): String {
        return birthdate.toString()
    }

    fun getPicture(): Int? {
        return picture
    }
}