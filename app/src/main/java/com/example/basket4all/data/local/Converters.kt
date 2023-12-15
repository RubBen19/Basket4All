package com.example.basket4all.data.local

import androidx.room.TypeConverter
import com.example.basket4all.common.classes.Score
import com.example.basket4all.common.enums.CoachRoles
import com.example.basket4all.common.enums.PlayerPositions
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Clase con todos los métodos [TypeConverter] para poder almacenar y hacer las referencias
 * a tipos de datos complejos
 */
class Converters {

    /** Convertidores para variables [LocalDate] en [String] y viceversa **/

    // Formato para las fechas
    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    // Gson para las conversiones Json
    private val gson = Gson()

    @TypeConverter
    fun fromLocalDate(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(value, dateFormatter) }
    }

    @TypeConverter
    fun toLocalDate(date: LocalDate?): String? {
        return date?.format(dateFormatter)
    }

    /** Convertidores para listas [PlayerPositions] en [String] con [Gson] y viceversa **/
    @TypeConverter
    fun fromJsonToPlayerPosition(value: String?): MutableList<PlayerPositions> {
        return if (value == null) mutableListOf()
        else {
            val type = object : TypeToken<MutableList<PlayerPositions>>() {}.type
            return gson.fromJson(value, type)
        }
    }

    @TypeConverter
    fun toJsonFromPlayerPosition(list: MutableList<PlayerPositions>): String {
        return gson.toJson(list)
    }

    /** Convertidores para variables [Score] en [String] con [Gson] y viceversa **/
    @TypeConverter
    fun fromJsonToScore (value: String?): Score {
        return gson.fromJson(value, object : TypeToken<Score>() {}.type) ?: Score(0,0)
    }

    @TypeConverter
    fun toJsonFromScore (score: Score): String {
        return gson.toJson(score)
    }

    /** Convertidores para listas [Int] en [String] con [Gson] y viceversa **/
    @TypeConverter
    fun fromJsonToInt(value: String?): List<Int> {
        return if (value == null) emptyList()
        else {
            val type = object : TypeToken<List<Int>>() {}.type
            gson.fromJson(value, type)
        }
    }

    @TypeConverter
    fun toJsonFromInt(list: List<Int>): String {
        return gson.toJson(list)
    }

    /** Convertidores para listas [CoachRoles] en [String] con [Gson] y viceversa **/
    @TypeConverter
    fun fromJsonToCoachRoles(value: String?): MutableList<CoachRoles> {
        return if (value == null) mutableListOf()
        else {
            val type = object : TypeToken<MutableList<CoachRoles>>() {}.type
            return gson.fromJson(value, type)
        }
    }

    @TypeConverter
    fun toJsonFromCoachRoles(list: MutableList<CoachRoles>): String {
        return gson.toJson(list)
    }
}