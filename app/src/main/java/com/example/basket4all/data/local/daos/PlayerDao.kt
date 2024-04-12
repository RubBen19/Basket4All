package com.example.basket4all.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.basket4all.common.enums.Categories
import com.example.basket4all.data.local.entities.PlayerEntity
import com.example.basket4all.data.local.relations.PlayerAndPlayerStats
import com.example.basket4all.data.local.relations.PlayerWithMatchStats
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(player: PlayerEntity)

    @Update
    suspend fun update(player: PlayerEntity)

    @Delete
    suspend fun delete(player: PlayerEntity)

    @Query("SELECT * FROM players_table")
    fun getAll(): Flow<List<PlayerEntity>>

    @Query("SELECT * FROM players_table WHERE id = :id")
    fun getByID(id: Int): Flow<PlayerEntity>

    @Query("SELECT * FROM players_table WHERE Name LIKE :name")
    fun getByName(name: String): Flow<List<PlayerEntity>>

    @Query("SELECT * FROM players_table WHERE Name LIKE :name AND " +
            "`First Surname` LIKE :surname1 AND `Second Surname` LIKE :surname2 LIMIT 1")
    fun getByName(name: String, surname1: String, surname2: String): Flow<PlayerEntity>

    @Query("SELECT * FROM players_table WHERE Category LIKE :category")
    fun getByCategory(category: Categories): Flow<List<PlayerEntity>>

    @Query("SELECT * FROM players_table WHERE email = :email")
    fun getByEmail(email: String) : Flow<PlayerEntity>

    @Transaction
    @Query("SELECT * " +
            "FROM players_table " +
            "INNER JOIN players_stats_table ON players_table.id = players_stats_table.Player")
    fun getPlayersAndStats(): Flow<List<PlayerAndPlayerStats>>

    @Transaction
    @Query("SELECT * " +
            "FROM players_table " +
            "INNER JOIN matches_stats_table ON players_table.id = matches_stats_table.Player")
    fun getPlayersWithMatchStats(): Flow<List<PlayerWithMatchStats>>

}