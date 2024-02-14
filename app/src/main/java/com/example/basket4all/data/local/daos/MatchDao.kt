package com.example.basket4all.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.basket4all.data.local.entities.MatchEntity
import com.example.basket4all.data.local.relations.MatchWithMatchStats
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(match: MatchEntity)

    @Delete
    suspend fun delete(match: MatchEntity)

    @Update
    suspend fun update(match: MatchEntity)

    @Query("SELECT * FROM matches_tables")
    fun getAll(): Flow<List<MatchEntity>>

    @Query("SELECT * FROM matches_tables WHERE id = :id")
    fun getByID(id: Int): Flow<MatchEntity>

    @Query("SELECT * FROM matches_tables WHERE Local = :teamId OR Visitor = :teamId")
    fun getByTeam(teamId: Int): Flow<List<MatchEntity>>

    @Query("SELECT * FROM matches_tables WHERE Date LIKE :date")
    fun getByDate(date: String): Flow<List<MatchEntity>>

    @Transaction
    @Query("SELECT * FROM matches_tables WHERE id IN " +
            "(SELECT DISTINCT `Match` FROM matches_stats_table)")
    fun getMatchesWithStats(): Flow<List<MatchWithMatchStats>>
}