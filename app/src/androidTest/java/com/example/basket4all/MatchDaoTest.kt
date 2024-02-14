package com.example.basket4all

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.basket4all.common.classes.Score
import com.example.basket4all.common.enums.Categories
import com.example.basket4all.data.local.AppDatabase
import com.example.basket4all.data.local.daos.MatchDao
import com.example.basket4all.data.local.entities.ClubEntity
import com.example.basket4all.data.local.entities.MatchEntity
import com.example.basket4all.data.local.entities.MatchStats
import com.example.basket4all.data.local.entities.PlayerEntity
import com.example.basket4all.data.local.entities.TeamEntity
import com.example.basket4all.data.local.entities.User
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class MatchDaoTest {

    private lateinit var matchDao: MatchDao
    private lateinit var appDatabase: AppDatabase
    private lateinit var teamA: TeamEntity
    private lateinit var teamB: TeamEntity

    @OptIn(DelicateCoroutinesApi::class)
    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        matchDao = appDatabase.matchDao()
        val club = ClubEntity(id = 1, name = "ClubA", description = "Club de prueba A")
        teamA = TeamEntity(teamId = 1, clubId = club.id, name = "Team A",
            category = Categories.CADETE, league = "Test")
        teamB = TeamEntity(teamId = 2, clubId = club.id, name = "Team B",
            category = Categories.CADETE, league = "Test")

        GlobalScope.launch(Dispatchers.IO) {
            appDatabase.clubDao().insert(club)
            appDatabase.teamDao().insert(teamA)
            appDatabase.teamDao().insert(teamB)
        }
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertMatch() = runBlocking {
        // Se crea un partido
        val score = Score(72, 68)
        val match = MatchEntity(id = 2, localTeamId = teamA.teamId, visitorTeamId = teamB.teamId,
            date = LocalDate.of(2023, 12, 29), score = score)

        // Se inserta
        matchDao.insert(match)

        // Se busca en la base de datos con el id para comprobar si se encuentra el mismo partido
        val loadedMatch = matchDao.getByID(2).first()
        assertEquals("INSERT FAILED",match, loadedMatch)
    }

    @Test
    @Throws(Exception::class)
    fun deleteMatch() = runBlocking {
        // Se crea un partido
        val score = Score(72, 68)
        val match = MatchEntity(id = 2, localTeamId = teamA.teamId, visitorTeamId = teamB.teamId,
            date = LocalDate.of(2023, 12, 29), score = score)

        // Se inserta
        matchDao.insert(match)

        // Se elimina y se comprueba que ahora no hay nada
        matchDao.delete(match)
        assertNull("DELETE FAILED", matchDao.getByID(2).firstOrNull())
    }

    @Test
    @Throws(Exception::class)
    fun updateMatch() = runBlocking {
        // Se crea un partido
        val score = Score(72, 68)
        val match = MatchEntity(id = 2, localTeamId = teamA.teamId, visitorTeamId = teamB.teamId,
            date = LocalDate.of(2023, 12, 29), score = score)

        // Se inserta
        matchDao.insert(match)

        // Se modifican los equipos y el marcador
        score.setLocalScore(68)
        score.setVisitorScore(72)
        val updatedMatch = MatchEntity(id = 2, localTeamId = teamB.teamId, visitorTeamId = teamA.teamId,
            date = LocalDate.of(2023, 12, 29), score = score)

        // Se actualiza en la base de datos y se comprueban los valores
        matchDao.update(updatedMatch)
        val loadedMatch = matchDao.getByID(2).first()
        assertEquals("UPDATE FAILED", loadedMatch, updatedMatch)
        assertEquals("UPDATE LOCAL FAILED", loadedMatch.localTeamId, match.visitorTeamId)
        assertEquals("UPDATE VISITOR FAILED", loadedMatch.visitorTeamId, match.localTeamId)
        assertEquals("UPDATE SCORE FAILED", loadedMatch.score, match.score)
        assertEquals("UPDATE SCORE POINTS FAILED", 68,
            loadedMatch.score.getLocalScore())
    }

    @Test
    @Throws(Exception::class)
    fun getAllMatches() = runBlocking {
        // Se crean varios partidos
        val score = Score (45, 65)
        val matchA = MatchEntity(localTeamId = teamA.teamId, visitorTeamId = teamB.teamId,
            date = LocalDate.of(2023, 12, 20), score = score)
        val matchB = MatchEntity(localTeamId = teamA.teamId, visitorTeamId = teamB.teamId,
            date = LocalDate.of(2023, 12, 25), score = score)
        val matchC = MatchEntity(localTeamId = teamA.teamId, visitorTeamId = teamB.teamId,
            date = LocalDate.of(2023, 12, 29), score = score)

        // Se insertan
        matchDao.insert(matchA)
        matchDao.insert(matchB)
        matchDao.insert(matchC)

        // Se recuperan y se comprueban que han sido insertados los tres
        val matches = matchDao.getAll().first()
        assertEquals("GET ALL FAILED", 3, matches.size)
        // Se comprueba que se han insertado correctamente
        val loadedMatchA = matches.firstOrNull { it.date == matchA.date }
        assertNotNull("GET_ALL FAILED (A)", loadedMatchA)
        val loadedMatchB = matches.firstOrNull { it.date == matchB.date }
        assertNotNull("GET_ALL FAILED (B)", loadedMatchB)
        val loadedMatchC = matches.firstOrNull { it.date == matchC.date }
        assertNotNull("GET_ALL FAILED (C)", loadedMatchC)
    }

    @Test
    @Throws(Exception::class)
    fun getMatchesByID() = runBlocking {
        // Se crean varios partidos
        val score = Score (45, 65)
        val matchA = MatchEntity(id = 1, localTeamId = teamA.teamId, visitorTeamId = teamB.teamId,
            date = LocalDate.of(2023, 12, 20), score = score)
        val matchB = MatchEntity(id = 2, localTeamId = teamA.teamId, visitorTeamId = teamB.teamId,
            date = LocalDate.of(2023, 12, 25), score = score)
        val matchC = MatchEntity(id = 3, localTeamId = teamA.teamId, visitorTeamId = teamB.teamId,
            date = LocalDate.of(2023, 12, 29), score = score)

        // Se insertan
        matchDao.insert(matchA)
        matchDao.insert(matchB)
        matchDao.insert(matchC)

        // Se obtienen y se comprueban
        val loadedMatchA = matchDao.getByID(1).first()
        assertEquals("GET_BY_ID FAILED (A)", loadedMatchA, matchA)
        val loadedMatchB = matchDao.getByID(2).first()
        assertEquals("GET_BY_ID FAILED (B)", loadedMatchB, matchB)
        val loadedMatchC = matchDao.getByID(3).first()
        assertEquals("GET_BY_ID FAILED (C)", loadedMatchC, matchC)
    }

    @Test
    @Throws(Exception::class)
    fun getMatchesByTeam() = runBlocking {
        // Se crean dos nuevos equipos
        val teamC = TeamEntity(teamId = 3, clubId = 3, name = "Team C",
            category = Categories.CADETE, league = "Test")
        val teamD = TeamEntity(teamId = 4, clubId = 4, name = "Team D",
            category = Categories.CADETE, league = "Test")

        // Se crean varios partidos
        val score = Score (45, 65)
        val matchA = MatchEntity(id = 1, localTeamId = teamB.teamId, visitorTeamId = teamA.teamId,
            date = LocalDate.of(2023, 12, 20), score = score)
        val matchB = MatchEntity(id = 2, localTeamId = teamA.teamId, visitorTeamId = teamB.teamId,
            date = LocalDate.of(2023, 12, 25), score = score)
        val matchC = MatchEntity(id = 3, localTeamId = teamA.teamId, visitorTeamId = teamC.teamId,
            date = LocalDate.of(2023, 12, 29), score = score)

        // Se insertan
        matchDao.insert(matchA)
        matchDao.insert(matchB)
        matchDao.insert(matchC)

        // Se obtienen los partidos del equipo A
        val loadedMatchesA = matchDao.getByTeam(teamA.teamId).first()
        assertEquals("GET_BY_TEAM FAILED SIZE (A)",3, loadedMatchesA.size)
        assertTrue("GET_BY_TEAM FAILED MATCH_A (A)", loadedMatchesA.contains(matchA))
        assertTrue("GET_BY_TEAM FAILED MATCH_B (A)", loadedMatchesA.contains(matchB))
        assertTrue("GET_BY_TEAM FAILED MATCH_C (A)", loadedMatchesA.contains(matchC))

        // Se obtienen los partidos del equipo B
        val loadedMatchesB = matchDao.getByTeam(teamB.teamId).first()
        assertEquals("GET_BY_TEAM FAILED SIZE (B)",2, loadedMatchesB.size)
        assertTrue("GET_BY_TEAM FAILED MATCH_A (B)", loadedMatchesB.contains(matchA))
        assertTrue("GET_BY_TEAM FAILED MATCH_B (B)", loadedMatchesB.contains(matchB))

        // Se obtienen los partidos del equipo C
        val loadedMatchesC = matchDao.getByTeam(teamC.teamId).first()
        assertEquals("GET_BY_TEAM FAILED SIZE (C)",1, loadedMatchesC.size)
        assertTrue("GET_BY_TEAM FAILED MATCH_C (C)", loadedMatchesC.contains(matchC))

        // Se obtienen los partidos del equipo D
        val loadedMatchesD = matchDao.getByTeam(teamD.teamId).first()
        assertEquals("GET_BY_TEAM FAILED SIZE (C)",0, loadedMatchesD.size)
    }

    @Test
    @Throws(Exception::class)
    fun getMatchesByDate() = runBlocking {
        // Se crean un par de fechas
        val dateA = LocalDate.of(2023, 12, 20)
        val dateB = LocalDate.of(2023, 10, 25)
        val dateD = LocalDate.of(2024, 10, 25)

        // Se crean varios partidos
        val score = Score (55, 63)
        val matchA = MatchEntity(id = 1, localTeamId = teamB.teamId, visitorTeamId = teamA.teamId,
            date = dateA, score = score)
        val matchB = MatchEntity(id = 2, localTeamId = teamA.teamId, visitorTeamId = teamB.teamId,
            date = dateB, score = score)
        val matchC = MatchEntity(id = 3, localTeamId = teamA.teamId, visitorTeamId = teamB.teamId,
            date = dateA, score = score)

        // Se insertan
        matchDao.insert(matchA)
        matchDao.insert(matchB)
        matchDao.insert(matchC)

        // Se obtienen los partidos jugados en la fecha A
        val loadedMatchesA = matchDao.getByDate(dateA.toString()).first()
        assertEquals("GET_BY_DATE FAILED SIZE (A)",2, loadedMatchesA.size)
        assertTrue("GET_BY_DATE FAILED MATCH_A (A)", loadedMatchesA.contains(matchA))
        assertTrue("GET_BY_DATE FAILED MATCH_C (A)", loadedMatchesA.contains(matchC))

        // Se obtienen los partidos jugados en la fecha B
        val loadedMatchesB = matchDao.getByDate(dateB.toString()).first()
        assertEquals("GET_BY_DATE FAILED SIZE (B)",1, loadedMatchesB.size)
        assertTrue("GET_BY_DATE FAILED MATCH_B (B)", loadedMatchesB.contains(matchB))

        // Se obtienen los partidos jugados en la fecha D
        val loadedMatchesD = matchDao.getByDate(dateD.toString()).first()
        assertEquals("GET_BY_DATE FAILED SIZE (B)",0, loadedMatchesD.size)
    }

    @Test
    @Throws(Exception::class)
    fun getMatchesWithStats() = runBlocking {
        // Se crean varios partidos
        val score = Score (45, 65)
        val matchA = MatchEntity(id = 1, localTeamId = teamB.teamId, visitorTeamId = teamA.teamId,
            date = LocalDate.of(2023, 12, 20), score = score)
        val matchB = MatchEntity(id = 2, localTeamId = teamA.teamId, visitorTeamId = teamB.teamId,
            date = LocalDate.of(2023, 12, 25), score = score)
        val matchC = MatchEntity(id = 3, localTeamId = teamA.teamId, visitorTeamId = teamB.teamId,
            date = LocalDate.of(2023, 12, 29), score = score)

        // Se insertan
        matchDao.insert(matchA)
        matchDao.insert(matchB)
        matchDao.insert(matchC)

        // Se crean un par de jugadores
        val user = User(email = "", password = "", name = "User", surname1 = "", surname2 = null,
            birthdate = LocalDate.of(2001, 1, 9), picture = null)
        val player1 = PlayerEntity(id = 1, user = user, teamId = teamA.teamId)
        val player2 = PlayerEntity(id = 2, user = user, teamId = teamA.teamId)
        val player3 = PlayerEntity(id = 3, user = user, teamId = teamB.teamId)

        // Se insertan
        appDatabase.playerDao().insert(player1)
        appDatabase.playerDao().insert(player2)
        appDatabase.playerDao().insert(player3)

        // Se crean estadísticas para los partidos A y B
        val statsMatchA1 = MatchStats(id = 1, matchId = matchA.id, playerId = 1)
        val statsMatchA2 = MatchStats(id = 2, matchId = matchA.id, playerId = 2)
        val statsMatchA3 = MatchStats(id = 3, matchId = matchA.id, playerId = 3)
        val statsMatchB1 = MatchStats(id = 4, matchId = matchB.id, playerId = 1)

        // Se insertan las estadísticas
        appDatabase.matchStatsDao().insert(statsMatchA1)
        appDatabase.matchStatsDao().insert(statsMatchA2)
        appDatabase.matchStatsDao().insert(statsMatchA3)
        appDatabase.matchStatsDao().insert(statsMatchB1)

        // Se obtienen los partidos con estadísticas
        val matchesWithStats = matchDao.getMatchesWithStats().first()
        assertEquals("WITH_MATCHES FAILED SIZE", 2, matchesWithStats.size)
        assertNotNull("WITH_MATCHES FAILED (A)",
            matchesWithStats.firstOrNull { it.match == matchA })
        assertNotNull("WITH_MATCHES FAILED (B)",
            matchesWithStats.firstOrNull { it.match == matchB })
        assertNull("WITH_MATCHES FAILED (C)",
            matchesWithStats.firstOrNull { it.match == matchC })
        assertEquals("WITH_MATCHES FAILED SIZE (A)", 3,
            matchesWithStats.first { it.match == matchA }.matchesStats.size)
        assertEquals("WITH_MATCHES FAILED SIZE (B)", 1,
            matchesWithStats.first { it.match == matchB }.matchesStats.size)
    }
}