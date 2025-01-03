package com.example.basket4all.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.basket4all.data.local.entities.MatchStats
import com.example.basket4all.data.local.entities.PlayerStats
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerStatsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(player: PlayerStats)

    @Delete
    suspend fun delete(player: PlayerStats)

    @Update
    suspend fun update(player: PlayerStats)

    @Query("SELECT * FROM players_stats_table")
    fun getAll(): Flow<List<PlayerStats>>

    @Query("SELECT * FROM players_stats_table WHERE id = :id")
    fun getByID(id: Int): Flow<PlayerStats>

    @Query("SELECT * FROM players_stats_table WHERE Player = :playerId")
    fun getByPlayer(playerId: Int): Flow<PlayerStats>
}