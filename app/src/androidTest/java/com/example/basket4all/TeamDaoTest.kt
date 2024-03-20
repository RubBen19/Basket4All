package com.example.basket4all

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.basket4all.common.classes.Score
import com.example.basket4all.common.enums.Categories
import com.example.basket4all.common.enums.CoachRoles
import com.example.basket4all.data.local.AppDatabase
import com.example.basket4all.data.local.daos.TeamDao
import com.example.basket4all.data.local.entities.CoachEntity
import com.example.basket4all.data.local.entities.CoachTeamCrossRef
import com.example.basket4all.data.local.entities.MatchEntity
import com.example.basket4all.data.local.entities.PlayerEntity
import com.example.basket4all.data.local.entities.TeamEntity
import com.example.basket4all.data.local.entities.TeamStats
import com.example.basket4all.data.local.entities.User
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.util.Formatter

/**
 * Clase de pruebas de [TeamDao], se realizarán tests para las siguientes funciones:
 *      - Insert
 *      - Update
 *      - Delete
 *      - GetAll
 *      - GetById
 *      - GetByName
 *      - GetByCategory
 *      - GetByLeague
 *      - GetTeamsAndStats
 *      - GetTeamsWithPlayers
 *      - GetTeamsWithMatches
 *      - GetTeamsWithCoaches
 */
@RunWith(AndroidJUnit4::class)
class TeamDaoTest {
    //Declaración de variables
    private lateinit var teamDao: TeamDao
    private lateinit var database: AppDatabase

    @Before
    fun setup() {
        //Se obtiene el contexto
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        //Construcción de la base de datos
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        //Inicio de la variable teamDao con el Dao de Team
        teamDao = database.teamDao()
    }

    @After
    fun tearDown() {
        //Cierre de la base de datos de las pruebas
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertTeam() = runBlocking {
        //Creación de un equipo
        val team = TeamEntity(1,1,"TEST CB", Categories.SENIOR, "ACB")
        //Comprobación de que la base de datos esta vacía
        assertEquals("O TEAMS IN DB ERROR", 0, teamDao.getAll().first().size)
        //Inserción del equipo de pruebas
        teamDao.insert(team)
        //Se comprueba que el elemento insertado es el que debería ser
        assertEquals("UNKNOWN TEAM ERROR", team, teamDao.getAll().first().first())
    }

    @Test
    @Throws(Exception::class)
    fun updateTeam() = runBlocking {
        val team = TeamEntity(1,1,"TEST CB", Categories.SENIOR, "ACB")
        teamDao.insert(team)
        //Obtención de un equipo por su id
        var teamObt = teamDao.getByID(1).first()
        assertEquals("TEAM OBTAINED FAILED", team, teamObt)
        //Modificación del equipo
        val teamUpdated = TeamEntity(1,1,"TEST CB", Categories.SENIOR, "LEB PLATA")
        //Se actualiza la modificación en la base de datos
        teamDao.update(teamUpdated)
        teamObt = teamDao.getByID(1).first()
        //Se comprueba que la modificación se ha realizado
        assertEquals("ISN'T THE SAME", teamUpdated, teamObt)
    }

    @Test
    @Throws(Exception::class)
    fun deleteTeam() = runBlocking {
        val team = TeamEntity(1,1,"TEST CB", Categories.SENIOR, "ACB")
        teamDao.insert(team)
        //Se elimina el elemento insertado
        teamDao.delete(team)
        //Como solo había un elemento, se comprueba que ya no hay estadísticas de jugador en la BD
        assertEquals("TEAM DELETE FAILED", 0, teamDao.getAll().first().size)
    }

    @Test
    @Throws(Exception::class)
    fun getByName() = runBlocking {
        //Creación de 2 equipos con distintos nombres
        val team = TeamEntity(1,1,"TEST CB", Categories.SENIOR, "ACB")
        val team2 = TeamEntity(2,1,"PRUEBA CB", Categories.SENIOR, "ACB")
        teamDao.insert(team2)
        teamDao.insert(team)
        //Se busca el Test CB en la base
        val teamObt = teamDao.getByName("TEST CB").first().first()
        assertEquals("ISN'T TEST CB", team, teamObt)
    }

    @Test
    @Throws(Exception::class)
    fun getByCategory() = runBlocking {
        //Creación de 3 equipos con distintas categorias
        val team = TeamEntity(1,1,"TEST CB", Categories.SENIOR, "ACB")
        val team2 = TeamEntity(2,1,"PRUEBA CB", Categories.SUB22, "ACB")
        val team3 = TeamEntity(3,2,"TEST2 CB", Categories.SENIOR, "ACB")
        teamDao.insert(team)
        teamDao.insert(team2)
        teamDao.insert(team3)
        //Se buscan los equipos Senior y Sub22
        val seniorTeams = teamDao.getByCategory(Categories.SENIOR).first()
        val sub22Teams = teamDao.getByCategory(Categories.SUB22).first()
        //Se comprueba que la lista de equipos senior devuelve lo esperado
        assertEquals(2, seniorTeams.size)
        assertEquals(true, seniorTeams.contains(team))
        assertEquals(true, seniorTeams.contains(team3))
        //Se comprueba que la lista de equipos sub22 devuelve lo esperado
        assertEquals(1, sub22Teams.size)
        assertEquals(true, sub22Teams.contains(team2))
    }

    @Test
    @Throws(Exception::class)
    fun getByLeague() = runBlocking {
        //Creación de 3 equipos con distintas categorias
        val team = TeamEntity(1,1,"TEST CB", Categories.SENIOR, "ACB")
        val team2 = TeamEntity(2,1,"PRUEBA CB", Categories.SUB22, "LEB ORO")
        val team3 = TeamEntity(3,2,"TEST2 CB", Categories.SENIOR, "ACB")
        teamDao.insert(team)
        teamDao.insert(team2)
        teamDao.insert(team3)
        //Se piden los equipos de ACB y LEB ORO
        val acbTeams = teamDao.getByLeague("ACB").first()
        val oroTeams = teamDao.getByLeague("LEB ORO").first()
        //Se comprueban los valores de la lista de equipos ACB
        assertEquals(2, acbTeams.size)
        assertEquals(true, acbTeams.contains(team))
        assertEquals(true, acbTeams.contains(team3))
        //Se comprueban los valores de la lista de equipos LEB ORO
        assertEquals(1, oroTeams.size)
        assertEquals(true, oroTeams.contains(team2))
    }

    @Test
    @Throws(Exception::class)
    fun getTeamsAndStats() = runBlocking {
        //Creación de 3 equipos
        val team = TeamEntity(1,1,"TEST CB", Categories.SENIOR, "ACB")
        val team2 = TeamEntity(2,1,"PRUEBA CB", Categories.SUB22, "LEB ORO")
        val team3 = TeamEntity(3,2,"TEST2 CB", Categories.SENIOR, "ACB")
        teamDao.insert(team)
        teamDao.insert(team2)
        teamDao.insert(team3)
        //Creación de estadisticas para dos equipos
        val teamStats = TeamStats(1,1)
        val teamStats3 = TeamStats(2,3)
        //Se insertan
        val teamStatsDao = database.teamStatsDao()
        teamStatsDao.insert(teamStats)
        teamStatsDao.insert(teamStats3)
        //Se solicitan los equipos que tienen estadísticas de equipo
        val teamsWithStats = teamDao.getTeamsAndStats().first()
        //Se comprueban los resultados
        assertEquals(2, teamsWithStats.size)
        //Equipo 1 y sus estadísticas está en la lista
        assertEquals(team, teamsWithStats.firstOrNull{ it.team == team }?.team)
        assertEquals(teamStats, teamsWithStats.firstOrNull{ it.team == team }?.stats)
        //Equipo 2 y sus estadísticas está en la lista
        assertEquals(team3, teamsWithStats.firstOrNull{ it.team == team3 }?.team)
        assertEquals(teamStats3, teamsWithStats.firstOrNull{ it.team == team3 }?.stats)
    }

    @Test
    @Throws(Exception::class)
    fun getTeamsWithPlayers() = runBlocking {
        //Creación de jugadores de prueba
        val userExample = User(
            email = "user@mail.com",
            password = "password",
            name = "User",
            surname1 = "Usurname",
            surname2 = "Usser",
            birthdate = LocalDate.of(2007, 1, 9),
            picture = null
        )
        val player = PlayerEntity(
            1,
            user = userExample,
            teamId = 1
        )
        val player2 = PlayerEntity(
            2,
            user = userExample,
            teamId = 1
        )
        val player3 = PlayerEntity(
            3,
            user = userExample,
            teamId = 2
        )
        //Se insertan en la base de datos
        val playerDao = database.playerDao()
        playerDao.insert(player)
        playerDao.insert(player2)
        playerDao.insert(player3)
        //Creación de 3 equipos
        val team = TeamEntity(1,1,"TEST CB", Categories.SENIOR, "ACB")
        val team2 = TeamEntity(2,1,"PRUEBA CB", Categories.SUB22, "LEB ORO")
        val team3 = TeamEntity(3,2,"TEST2 CB", Categories.SENIOR, "ACB")
        teamDao.insert(team)
        teamDao.insert(team2)
        teamDao.insert(team3)
        //Obtención de los equipos que tengan jugadores
        val teamsWithPlayers = teamDao.getTeamsWithPlayers().first()
        //Se comprueban resultados
        assertEquals(2, teamsWithPlayers.size)
        //Se verifica que el equipo 1 aparece y tiene a sus 2 jugadores
        assertEquals(team, teamsWithPlayers.firstOrNull{ it.team == team }?.team)
        assertEquals(true,
            teamsWithPlayers.firstOrNull{ it.team == team }?.players?.contains(player))
        assertEquals(true,
            teamsWithPlayers.firstOrNull{ it.team == team }?.players?.contains(player2))
        //Se verifica que el equipo 2 aparece y tiene a su jugador
        assertEquals(team2, teamsWithPlayers.firstOrNull{ it.team == team2 }?.team)
        assertEquals(true,
            teamsWithPlayers.firstOrNull{ it.team == team2 }?.players?.contains(player3))
    }

    @Test
    @Throws(Exception::class)
    fun getTeamsWithMatches() = runBlocking {
        //Creación de 4 equipos
        val team = TeamEntity(1,1,"TEST CB", Categories.SENIOR, "ACB")
        val team2 = TeamEntity(2,1,"PRUEBA CB", Categories.SUB22, "LEB ORO")
        val team3 = TeamEntity(3,2,"TEST2 CB", Categories.SENIOR, "ACB")
        val team4 = TeamEntity(4,3,"TEST3 CB", Categories.SENIOR, "ACB")
        teamDao.insert(team)
        teamDao.insert(team2)
        teamDao.insert(team3)
        teamDao.insert(team4)
        //Creación de varios partidos
        val date = LocalDate.of(2024, 10,1)
        val score = Score(0,0)
        val match1 = MatchEntity(1, 3, 1, date, score)
        val match2 = MatchEntity(2, 3, 4, date, score)
        val match3 = MatchEntity(3, 4, 3, date, score)
        //Se insertar los partidos
        val matchDao = database.matchDao()
        matchDao.insert(match1)
        matchDao.insert(match2)
        matchDao.insert(match3)
        //Se recuperan los equipos y los partidos jugados (si no han jugado ninguno no deben estar)
        val teamsWmatches = teamDao.getTeamsWithMatches().first()
        //Se comprueban resultados
        assertEquals(3, teamsWmatches.size)
        //Se comprueba que el equipo 1 esta junto a sus partidos
        assertEquals(team, teamsWmatches.firstOrNull{ it.team == team }?.team)
        assertEquals(true, teamsWmatches.firstOrNull{ it.team == team }
            ?.visitorMatches?.contains(match1))
        assertEquals(0, teamsWmatches.firstOrNull{ it.team == team }
            ?.localMatches?.size)
        //Se comprueba que el equipo 3 esta junto a sus partidos
        assertEquals(team3, teamsWmatches.firstOrNull{ it.team == team3 }?.team)
        assertEquals(true, teamsWmatches.firstOrNull{ it.team == team3 }
            ?.visitorMatches?.contains(match3))
        assertEquals(true, teamsWmatches.firstOrNull{ it.team == team3 }
            ?.localMatches?.contains(match1))
        assertEquals(true, teamsWmatches.firstOrNull{ it.team == team3 }
            ?.localMatches?.contains(match2))
        //Se comprueba que el equipo 4 esta junto a sus partidos
        assertEquals(team4, teamsWmatches.firstOrNull{ it.team == team4 }?.team)
        assertEquals(true, teamsWmatches.firstOrNull{ it.team == team4 }
            ?.visitorMatches?.contains(match2))
        assertEquals(true, teamsWmatches.firstOrNull{ it.team == team4 }
            ?.localMatches?.contains(match3))
    }

    @Test
    @Throws(Exception::class)
    fun getTeamsWithCoaches() = runBlocking {
        //Creación de 3 equipos
        val team = TeamEntity(1,1,"TEST CB", Categories.SENIOR, "ACB")
        val team2 = TeamEntity(2,1,"PRUEBA CB", Categories.SUB22, "LEB ORO")
        val team3 = TeamEntity(3,2,"TEST2 CB", Categories.SENIOR, "ACB")
        teamDao.insert(team)
        teamDao.insert(team2)
        teamDao.insert(team3)
        //Creacion de 2 entrenadores
        val userExample = User(
            email = "user@mail.com",
            password = "password",
            name = "User",
            surname1 = "Usurname",
            surname2 = "Usser",
            birthdate = LocalDate.of(2007, 1, 9),
            picture = null
        )
        val coach = CoachEntity(1, userExample)
        val coach2 = CoachEntity(2, userExample)
        //Se insertan en la base de datos
        val coachDao = database.coachDao()
        coachDao.insert(coach)
        coachDao.insert(coach2)
        //Creación de relaciones entre entrenadores y equipos con los roles correspondientes
        val crossref = CoachTeamCrossRef(1, 1, CoachRoles.MAIN)
        val crossref2 = CoachTeamCrossRef(1, 2, CoachRoles.SECOND)
        val crossref3 = CoachTeamCrossRef(2, 2, CoachRoles.MAIN)
        val crossref4 = CoachTeamCrossRef(2, 1, CoachRoles.ESPECIALIST)
        //Se insertan
        val coachTeamCrossRefDao = database.coachTeamCrossRefDao()
        coachTeamCrossRefDao.insert(crossref)
        coachTeamCrossRefDao.insert(crossref2)
        coachTeamCrossRefDao.insert(crossref3)
        coachTeamCrossRefDao.insert(crossref4)
        //Se comprueban resultados
        val teamsWcoaches = teamDao.getTeamsWithCoaches().first()
        assertEquals(2, teamsWcoaches.size)
        //Se comprueba que el equipo 1 está junto sus entrenadores
        assertEquals(team, teamsWcoaches.firstOrNull { it.team == team }?.team)
        assertEquals(true, teamsWcoaches.firstOrNull { it.team == team }
            ?.coaches?.contains(coach))
        assertEquals(true, teamsWcoaches.firstOrNull { it.team == team }
            ?.coaches?.contains(coach2))
        //Se comprueba que el equipo 2 está junto sus entrenadores
        assertEquals(team2, teamsWcoaches.firstOrNull { it.team == team2 }?.team)
        assertEquals(true, teamsWcoaches.firstOrNull { it.team == team2 }
            ?.coaches?.contains(coach))
        assertEquals(true, teamsWcoaches.firstOrNull { it.team == team2 }
            ?.coaches?.contains(coach2))
    }
}