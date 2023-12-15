package com.example.basket4all.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.basket4all.common.enums.Categories
import com.example.basket4all.data.local.entities.TeamEntity
import com.example.basket4all.data.local.relations.TeamAndTeamStats
import com.example.basket4all.data.local.relations.TeamWithCoaches
import com.example.basket4all.data.local.relations.TeamWithMatches
import com.example.basket4all.data.local.relations.TeamWithPlayers
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(team: TeamEntity)

    @Update
    suspend fun update(team: TeamEntity)

    @Delete
    suspend fun delete(team: TeamEntity)

    @Query("SELECT * FROM teams_table")
    fun getAll(): Flow<List<TeamEntity>>

    @Query("SELECT * FROM teams_table WHERE id = :id")
    fun getByID(id: Int): Flow<TeamEntity>

    @Query("SELECT * FROM teams_table WHERE Name LIKE :name")
    fun getByName(name: String): Flow<List<TeamEntity>>

    @Query("SELECT * FROM teams_table WHERE Category = :category")
    fun getByCategory(category: Categories): Flow<List<TeamEntity>>

    @Query("SELECT * FROM teams_table WHERE League LIKE :league")
    fun getByLeague(league: String): Flow<List<TeamEntity>>

    @Transaction
    @Query("SELECT * FROM teams_table")
    fun getTeamsAndStats(): Flow<List<TeamAndTeamStats>>

    @Transaction
    @Query("SELECT * FROM teams_table")
    fun getTeamsWithPlayers(): Flow<List<TeamWithPlayers>>

    @Transaction
    @Query("SELECT * FROM teams_table")
    fun getTeamsWithMatches(): Flow<List<TeamWithMatches>>

    @Transaction
    @Query("SELECT * FROM teams_table")
    fun getTeamsWithCoaches(): Flow<List<TeamWithCoaches>>
}