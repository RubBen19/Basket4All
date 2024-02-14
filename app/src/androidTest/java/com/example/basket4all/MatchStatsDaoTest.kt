package com.example.basket4all

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.basket4all.common.classes.Score
import com.example.basket4all.common.enums.Categories
import com.example.basket4all.data.local.AppDatabase
import com.example.basket4all.data.local.daos.MatchStatsDao
import com.example.basket4all.data.local.entities.ClubEntity
import com.example.basket4all.data.local.entities.MatchEntity
import com.example.basket4all.data.local.entities.MatchStats
import com.example.basket4all.data.local.entities.PlayerEntity
import com.example.basket4all.data.local.entities.TeamEntity
import com.example.basket4all.data.local.entities.User
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class MatchStatsDaoTest {

    private lateinit var matchStatsDaoTest: MatchStatsDao
    private lateinit var appDatabase: AppDatabase
    private lateinit var match: MatchEntity
    private lateinit var player: PlayerEntity

    @OptIn(DelicateCoroutinesApi::class)
    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        matchStatsDaoTest = appDatabase.matchStatsDao()
        // Creación de variables para pruebas
        val club = ClubEntity(id = 1, name = "Club", description = "Club de prueba")
        val teamA = TeamEntity(teamId = 1, clubId = club.id, name = "Team A",
            category = Categories.CADETE, league = "Test")
        val teamB = TeamEntity(teamId = 2, clubId = club.id, name = "Team B",
            category = Categories.CADETE, league = "Test")

        match = MatchEntity(
            id = 1,
            localTeamId = teamA.teamId,
            visitorTeamId = teamB.teamId,
            date = LocalDate.of(2023, 12, 29),
            score = Score(68,57)
        )

        val user = User(email = "", password = "", name = "User", surname1 = "", surname2 = null,
            birthdate = LocalDate.of(2001, 1, 9), picture = null)

        player = PlayerEntity(id = 1, user = user, teamId = teamA.teamId)

        GlobalScope.launch(Dispatchers.IO) {
            appDatabase.clubDao().insert(club)
            appDatabase.teamDao().insert(teamA)
            appDatabase.teamDao().insert(teamB)
            appDatabase.playerDao().insert(player)
            appDatabase.matchDao().insert(match)
        }
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertMatchStats() = runBlocking {
        val matchStat = MatchStats(id = 1, match.id, player.id)
        matchStatsDaoTest.insert(matchStat)
        val loadedMatchStat = matchStatsDaoTest.getAll().first().first()
        assertEquals("INSERT FAILED", matchStat, loadedMatchStat)
    }

    @Test
    @Throws(Exception::class)
    fun deleteMatchStats() = runBlocking {
        val matchStat = MatchStats(id = 1, match.id, player.id)
        val matchStat2 = MatchStats(id = 2, match.id, player.id)
        matchStatsDaoTest.insert(matchStat)
        matchStatsDaoTest.insert(matchStat2)
        assertEquals("INSERT ERROR", 2, matchStatsDaoTest.getAll().first().size)
        matchStatsDaoTest.delete(matchStat)
        assertEquals("DELETE ERROR", 1, matchStatsDaoTest.getAll().first().size)
        val loadedMatchStat = matchStatsDaoTest.getAll().first().first()
        assertEquals("DELETE FAILED", matchStat2, loadedMatchStat)
    }

    @Test
    @Throws(Exception::class)
    fun updateMatchStats() = runBlocking {
        val matchStat = MatchStats(id = 1, match.id, player.id)
        matchStatsDaoTest.insert(matchStat)

        val club = ClubEntity(id = 1, name = "Club", description = "Club de prueba")
        val teamA = TeamEntity(teamId = 1, clubId = club.id, name = "Team A",
            category = Categories.CADETE, league = "Test")
        val teamB = TeamEntity(teamId = 2, clubId = club.id, name = "Team B",
            category = Categories.CADETE, league = "Test")
        val match2 = MatchEntity(2, teamA.teamId, teamB.teamId,
            LocalDate.of(2024, 10, 23), Score(45, 65))
        val matchStat2 = MatchStats(1, match2.id, player.id)

        matchStatsDaoTest.update(matchStat2)
        assertEquals("UPDATE ERROR", 1, matchStatsDaoTest.getAll().first().size)
        val loadedStat = matchStatsDaoTest.getAll().first().first()
        assertEquals("UPDATE FAILED", matchStat2, loadedStat)
    }

    @Test
    @Throws(Exception::class)
    fun getByID() = runBlocking {
        val matchStat = MatchStats(id = 1, match.id, player.id)
        val matchStat2 = MatchStats(id = 2, match.id, player.id)
        matchStatsDaoTest.insert(matchStat)
        matchStatsDaoTest.insert(matchStat2)
        val loadedMatchStat = matchStatsDaoTest.getByID(1).first()
        assertEquals("GET BY ID (1) FAILED", matchStat, loadedMatchStat)
        val loadedMatchStat2 = matchStatsDaoTest.getByID(2).first()
        assertEquals("GET BY ID (2) FAILED", matchStat2, loadedMatchStat2)
    }

    @Test
    @Throws(Exception::class)
    fun getByMatch() = runBlocking {

    }

    @Test
    @Throws(Exception::class)
    fun getByPlayer() = runBlocking {
        val team = appDatabase.teamDao().getByID(1).first()
        val user = User(email = "", password = "", name = "User2", surname1 = "", surname2 = null,
            birthdate = LocalDate.of(2001, 1, 9), picture = null)
        val player2 = PlayerEntity(id = 2, user = user, teamId = team.teamId)
        appDatabase.playerDao().insert(player2)

        val matchStat = MatchStats(id = 1, match.id, player.id)
        val matchStat2 = MatchStats(id = 2, match.id, player.id)
        val matchStat3 = MatchStats(id = 3, match.id, player2.id)
        val matchStat4 = MatchStats(id = 4, match.id, player.id)
        matchStatsDaoTest.insert(matchStat)
        matchStatsDaoTest.insert(matchStat2)
        matchStatsDaoTest.insert(matchStat3)
        matchStatsDaoTest.insert(matchStat4)

        val matchStatsOfPlayer = matchStatsDaoTest.getByPlayer(player.id).first()
        assertEquals("STATS MATCHES OF PLAYER 1 ERROR", 3, matchStatsOfPlayer.size)
        assertEquals("NOT CONTAINS STATS 1", true,
            matchStatsOfPlayer.contains(matchStat))
        assertEquals("NOT CONTAINS STATS 2", true,
            matchStatsOfPlayer.contains(matchStat2))
        assertEquals("NOT CONTAINS STATS 4", true,
            matchStatsOfPlayer.contains(matchStat4))

        val matchStatsOfPlayer2 = matchStatsDaoTest.getByPlayer(player2.id).first()
        assertEquals("STATS MATCHES OF PLAYER 2 ERROR", 1, matchStatsOfPlayer2.size)
        assertEquals("NOT CONTAINS STATS 3", true,
            matchStatsOfPlayer2.contains(matchStat3))
    }
}