package com.example.basket4all.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.basket4all.data.local.entities.MatchStats
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchStatsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stats: MatchStats)

    @Delete
    suspend fun delete(stats: MatchStats)

    @Update
    suspend fun update(stats: MatchStats)

    @Query("SELECT * FROM matches_stats_table")
    fun getAll(): Flow<List<MatchStats>>

    @Query("SELECT * FROM matches_stats_table WHERE id = :id")
    fun getByID(id: Int): Flow<MatchStats>

    @Query("SELECT * FROM matches_stats_table WHERE `Match` = :matchId")
    fun getByMatch(matchId: Int): Flow<MatchStats>

    @Query("SELECT * FROM matches_stats_table WHERE Player = :playerId")
    fun getByPlayer(playerId: Int): Flow<List<MatchStats>>
}