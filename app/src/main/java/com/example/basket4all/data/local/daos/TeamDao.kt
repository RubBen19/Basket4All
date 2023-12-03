package com.example.basket4all.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.basket4all.common.enums.Categories
import com.example.basket4all.data.local.entities.TeamEntity

@Dao
interface TeamDao {

    @Query("SELECT * FROM teams_table")
    fun getAll(): List<TeamEntity>

    @Query("SELECT * FROM teams_table WHERE id = :id")
    fun getByID(id: Int): TeamEntity

    @Query("SELECT * FROM teams_table WHERE Name LIKE :name")
    fun getByName(name: String): List<TeamEntity>

    @Query("SELECT * FROM teams_table WHERE Category = :category")
    fun getByCategory(category: Categories): List<TeamEntity>

    @Query("SELECT * FROM teams_table WHERE League LIKE :league")
    fun getByLeague(league: String): List<TeamEntity>

    @Insert
    fun insert(team: TeamEntity)

    @Update
    fun update(team: TeamEntity)

    @Delete
    fun delete(team: TeamEntity)
}