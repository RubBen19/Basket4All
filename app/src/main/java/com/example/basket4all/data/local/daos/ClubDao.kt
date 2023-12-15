package com.example.basket4all.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.basket4all.data.local.entities.ClubEntity
import com.example.basket4all.data.local.relations.ClubWithTeams
import kotlinx.coroutines.flow.Flow

@Dao
interface ClubDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(club: ClubEntity)

    @Delete
    suspend fun delete(club: ClubEntity)

    @Update
    suspend fun update(club: ClubEntity)

    @Query("SELECT * FROM clubs_table")
    fun getAll(): Flow<List<ClubEntity>>

    @Query("SELECT * FROM clubs_table WHERE id = :id")
    fun getByID(id: Int): Flow<ClubEntity>

    @Query("SELECT * FROM clubs_table WHERE name LIKE :clubName")
    fun getByName(clubName: String): Flow<List<ClubEntity>>

    @Transaction
    @Query("SELECT * FROM clubs_table")
    fun getClubsWithTeams(): Flow<List<ClubWithTeams>>

}