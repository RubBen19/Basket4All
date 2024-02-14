package com.example.basket4all.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.basket4all.data.local.entities.CoachEntity
import com.example.basket4all.data.local.entities.CoachTeamCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface CoachTeamCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(coachTeamCrossRef: CoachTeamCrossRef)

    @Delete
    suspend fun delete(coachTeamCrossRef: CoachTeamCrossRef)

    @Update
    suspend fun update(coachTeamCrossRef: CoachTeamCrossRef)

    @Query("SELECT * FROM coach_team_table")
    fun getAll(): Flow<List<CoachTeamCrossRef>>
}