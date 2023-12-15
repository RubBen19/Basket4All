package com.example.basket4all.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.basket4all.data.local.entities.TeamStats
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamStatsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(team: TeamStats)

    @Delete
    suspend fun delete(team: TeamStats)

    @Update
    suspend fun update(team: TeamStats)

    @Query("SELECT * FROM teams_stats_table")
    fun getAll(): Flow<List<TeamStats>>

    @Query("SELECT * FROM teams_stats_table WHERE id = :id")
    fun getByID(id: Int): Flow<TeamStats>

    @Query("SELECT * FROM teams_stats_table WHERE Team = :teamId")
    fun getByTeam(teamId: Int): Flow<TeamStats>

}