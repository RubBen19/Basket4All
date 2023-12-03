package com.example.basket4all.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.basket4all.common.enums.Categories
import com.example.basket4all.data.local.entities.PlayerEntity

@Dao
interface PlayerDao {

    @Query("SELECT * FROM players_table")
    fun getAll(): List<PlayerEntity>

    @Query("SELECT * FROM players_table WHERE Name LIKE :name")
    fun getByName(name: String): List<PlayerEntity>

    @Query("SELECT * FROM players_table WHERE Name LIKE :name AND " +
            "`First Surname` LIKE :surname1 AND `Second Surname` LIKE :surname2 LIMIT 1")
    fun getByName(name: String, surname1: String, surname2: String): PlayerEntity

    @Query("SELECT * FROM players_table WHERE Category LIKE :category")
    fun getByCategory(category: Categories): PlayerEntity

    @Insert
    fun insert(player: PlayerEntity)

    @Update
    fun update(player: PlayerEntity)

    @Delete
    fun delete(player: PlayerEntity)
}