package com.example.basket4all.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.basket4all.data.local.entities.CoachEntity
import com.example.basket4all.data.local.relations.CoachWithTeam
import kotlinx.coroutines.flow.Flow

@Dao
interface CoachDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(club: CoachEntity)

    @Delete
    suspend fun delete(club: CoachEntity)

    @Update
    suspend fun update(club: CoachEntity)

    @Query("SELECT * FROM coaches_table")
    fun getAll(): Flow<List<CoachEntity>>

    @Query("SELECT * FROM coaches_table WHERE coachId = :id")
    fun getByID(id: Int): Flow<CoachEntity>

    @Query("SELECT * FROM coaches_table WHERE email = :email")
    fun getByEmail(email: String): Flow<CoachEntity>

    @Query("SELECT * FROM coaches_table WHERE name LIKE :cName AND + " +
            "`First Surname` LIKE :cSurname1 AND `Second Surname` LIKE :cSurname2 LIMIT 1")
    fun getByName(cName: String, cSurname1: String, cSurname2: String): Flow<CoachEntity>

    @Query("SELECT * FROM coaches_table WHERE Name LIKE :coachName")
    fun getByName(coachName: String): Flow<List<CoachEntity>>

    @Transaction
    @Query("SELECT * " +
            "FROM coaches_table " +
            "INNER JOIN (" +
            "SELECT DISTINCT coachId FROM coach_team_table) " +
            "AS coaches ON coaches_table.coachId = coaches.coachId")
    fun getCoachesWithTeams(): Flow<List<CoachWithTeam>>
}