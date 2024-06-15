package com.example.basket4all.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.basket4all.data.local.entities.CalendarEventEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface CalendarEventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(calendarEvent: CalendarEventEntity)

    @Delete
    suspend fun delete(calendarEvent: CalendarEventEntity)

    @Update
    suspend fun update(calendarEvent: CalendarEventEntity)

    @Query("SELECT * FROM events_table")
    fun getAll(): Flow<List<CalendarEventEntity>>

    @Query("SELECT * FROM events_table WHERE teamId = :teamId AND date = :date")
    fun getByDateTeam(teamId: Int, date: LocalDate): Flow<List<CalendarEventEntity>>

    @Query("SELECT * FROM events_table WHERE teamId = :teamId")
    fun getByTeam(teamId: Int): Flow<List<CalendarEventEntity>>
}