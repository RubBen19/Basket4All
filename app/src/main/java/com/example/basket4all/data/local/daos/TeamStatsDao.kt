package com.example.basket4all.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.basket4all.data.local.entities.TeamStats

@Dao
interface TeamStatsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(team: TeamStats)

    @Delete
    fun delete(team: TeamStats)

    @Update
    fun update(team: TeamStats)

    @Query("SELECT * FROM teams_stats_table")
    fun getAll(): List<TeamStats>

    @Query("SELECT * FROM teams_stats_table WHERE id = :id")
    fun getByID(id: Int): TeamStats

    @Query("SELECT * FROM teams_stats_table WHERE Team = :teamId")
    fun getByTeam(teamId: Int): TeamStats

}