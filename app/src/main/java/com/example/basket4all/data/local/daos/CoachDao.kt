package com.example.basket4all.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.basket4all.data.local.entities.CoachEntity

@Dao
interface CoachDao {

    @Insert
    fun insert(club: CoachEntity)

    @Delete
    fun delete(club: CoachEntity)

    @Update
    fun update(club: CoachEntity)

    @Query("SELECT * FROM coaches_table")
    fun getAll(): List<CoachEntity>

    @Query("SELECT * FROM coaches_table WHERE coachId = :id")
    fun getByID(id: Int): CoachEntity

    @Query("SELECT * FROM coaches_table WHERE name LIKE :cName AND + " +
            "`First Surname` LIKE :cSurname1 AND `Second Surname` LIKE :cSurname2 LIMIT 1")
    fun getByName(cName: String, cSurname1: String, cSurname2: String): CoachEntity

    @Query("SELECT * FROM coaches_table WHERE Name LIKE :coachName")
    fun getByName(coachName: String): List<CoachEntity>

}