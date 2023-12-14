package com.example.basket4all.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.basket4all.data.local.entities.ClubEntity
import com.example.basket4all.data.local.relations.ClubWithTeams

@Dao
interface ClubDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(club: ClubEntity)

    @Delete
    fun delete(club: ClubEntity)

    @Update
    fun update(club: ClubEntity)

    @Query("SELECT * FROM clubs_table")
    fun getAll(): List<ClubEntity>

    @Query("SELECT * FROM clubs_table WHERE id = :id")
    fun getByID(id: Int): ClubEntity

    @Query("SELECT * FROM clubs_table WHERE name LIKE :clubName")
    fun getByName(clubName: String): List<ClubEntity>

    @Transaction
    @Query("SELECT * FROM clubs_table")
    fun getClubsWithTeams(): List<ClubWithTeams>

}