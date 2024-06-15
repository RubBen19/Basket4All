package com.example.basket4all.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.basket4all.common.enums.EventType
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "events_table")
class CalendarEventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val teamId: Int,
    val date: LocalDate,
    val type: EventType,
    val description: String,
    val place: String?,
    val hour: LocalTime?
)