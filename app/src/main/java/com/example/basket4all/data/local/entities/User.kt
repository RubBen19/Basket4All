package com.example.basket4all.data.local.entities

import androidx.room.ColumnInfo
import java.time.LocalDate

data class User(
    @ColumnInfo(name = "Email") val email: String,
    @ColumnInfo(name = "Password") val password: String,
    @ColumnInfo(name = "Name") val name: String,
    @ColumnInfo(name = "First Surname") val surname1: String,
    @ColumnInfo(name = "Second Surname") val surname2: String?,
    @ColumnInfo(name = "Date of birth") val birthdate: LocalDate,
    @ColumnInfo(name = "Picture") val picture: Int
)
