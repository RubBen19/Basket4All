package com.example.basket4all.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.basket4all.data.local.entities.MatchStats

@Dao
interface MatchStatsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stats: MatchStats)

    @Delete
    fun delete(stats: MatchStats)

    @Update
    fun update(stats: MatchStats)

    @Query("SELECT * FROM matches_stats_table")
    fun getAll(): List<MatchStats>

    @Query("SELECT * FROM matches_stats_table WHERE id = :id")
    fun getByID(id: Int): MatchStats

    @Query("SELECT * FROM matches_stats_table WHERE `Match` = :matchId")
    fun getByMatch(matchId: Int): MatchStats

    @Query("SELECT * FROM matches_stats_table WHERE Player = :playerId")
    fun getByPlayer(playerId: Int): List<MatchStats>
}