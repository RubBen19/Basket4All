package com.example.basket4all.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.basket4all.data.local.entities.MatchEntity

@Dao
interface MatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(match: MatchEntity)

    @Delete
    fun delete(match: MatchEntity)

    @Update
    fun update(match: MatchEntity)

    @Query("SELECT * FROM matches_tables")
    fun getAll(): List<MatchEntity>

    @Query("SELECT * FROM matches_tables WHERE id = :id")
    fun getByID(id: Int): MatchEntity

    @Query("SELECT * FROM matches_tables WHERE Local = :teamId OR Visitor = :teamId")
    fun getByTeam(teamId: Int): List<MatchEntity>

    @Query("SELECT * FROM matches_tables WHERE Date LIKE :date")
    fun getByDate(date: String): List<MatchEntity>
}