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
    @ColumnInfo(name = "Picture", typeAffinity = ColumnInfo.BLOB) var picture: ByteArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (email != other.email) return false
        if (password != other.password) return false
        if (name != other.name) return false
        if (surname1 != other.surname1) return false
        if (surname2 != other.surname2) return false
        if (birthdate != other.birthdate) return false
        if (!picture.contentEquals(other.picture)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = email.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + surname1.hashCode()
        result = 31 * result + (surname2?.hashCode() ?: 0)
        result = 31 * result + birthdate.hashCode()
        result = 31 * result + picture.contentHashCode()
        return result
    }
}
