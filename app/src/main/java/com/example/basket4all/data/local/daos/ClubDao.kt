package com.example.basket4all.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.basket4all.data.local.entities.ClubEntity

@Dao
interface ClubDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(club: ClubEntity)

    @Delete
    fun delete(club: ClubEntity)

    @Update
    fun update(club: ClubEntity)

    @Query("SELECT * FROM clubes_table")
    fun getAll(): List<ClubEntity>

    @Query("SELECT * FROM clubes_table WHERE id = :id")
    fun getByID(id: Int): ClubEntity

    @Query("SELECT * FROM clubes_table WHERE name LIKE :clubName")
    fun getByName(clubName: String): List<ClubEntity>

}