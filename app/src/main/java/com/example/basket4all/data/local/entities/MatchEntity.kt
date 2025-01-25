package com.example.basket4all.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.basket4all.common.classes.MatchScore
import com.example.basket4all.common.classes.Score
import java.time.LocalDate

@Entity(tableName = "matches_tables")
data class MatchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("Local")
    val localTeamId: Int,
    @ColumnInfo("Visitor")
    val visitorTeamId: Int,
    @ColumnInfo("Date")
    val date: LocalDate,
    @ColumnInfo("Score")
    var score: MatchScore
)
