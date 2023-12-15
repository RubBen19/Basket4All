package com.example.basket4all.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.basket4all.data.local.entities.ClubEntity
import com.example.basket4all.data.local.entities.CoachEntity
import com.example.basket4all.data.local.entities.MatchEntity
import com.example.basket4all.data.local.entities.MatchStats
import com.example.basket4all.data.local.entities.PlayerEntity
import com.example.basket4all.data.local.entities.PlayerStats
import com.example.basket4all.data.local.entities.TeamEntity
import com.example.basket4all.data.local.entities.TeamStats

@Database(
    entities = [PlayerEntity::class, CoachEntity::class, TeamEntity::class, ClubEntity::class,
        MatchEntity::class, PlayerStats::class, MatchStats::class, TeamStats::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
}