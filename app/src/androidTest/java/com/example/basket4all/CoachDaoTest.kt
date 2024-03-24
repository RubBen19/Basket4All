package com.example.basket4all

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.basket4all.common.enums.Categories
import com.example.basket4all.common.enums.CoachRoles
import com.example.basket4all.data.local.AppDatabase
import com.example.basket4all.data.local.daos.CoachDao
import com.example.basket4all.data.local.entities.CoachEntity
import com.example.basket4all.data.local.entities.CoachTeamCrossRef
import com.example.basket4all.data.local.entities.TeamEntity
import com.example.basket4all.data.local.entities.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

/**
 * Clase de pruebas de [CoachDao], se realizarán tests para las siguientes funciones:
 *      - Insert
 *      - Update
 *      - Delete
 *      - GetAll
 *      - GetById
 *      - GetByName x2
 *      - GetCoachesWithTeams
 */
@RunWith(AndroidJUnit4::class)
class CoachDaoTest {
    //Declaración de variables
    private lateinit var coachDao: CoachDao
    private lateinit var appDatabase: AppDatabase
    private lateinit var user1: User
    private lateinit var user2: User

    @Before
    fun setup() {
        //Se obtiene el contexto
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        //Construcción de la base de datos
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        //Inicio de la variable coachDao con el Dao de Coach
        coachDao = appDatabase.coachDao()
        //Creación de dos datos de usuario para las pruebas
        user1 = User(
            email = "mail@mail.com",
            password = "password",
            name = "User1",
            surname1 = "Surname1",
            surname2 = "Surname2",
            birthdate = LocalDate.of(2001, 1, 9),
            picture = 0
        )
        user2 = User(
            email = "mail@mail.com",
            password = "password",
            name = "User2",
            surname1 = "Surname12",
            surname2 = "Surname22",
            birthdate = LocalDate.of(2002, 2, 10),
            picture = 1
        )
    }

    @After
    fun tearDown() {
        //Cierre de la base de datos de las pruebas
        appDatabase.close()
    }

    @Test
    fun insertCoach() = runBlocking {
        // Se crea un coach
        val coach = CoachEntity(user = user1)
        // Se inserta en la BD
        coachDao.insert(coach)
        // Se busca por el id (Como solo hay un coach debería tener el id = 1)
        val loadedCoach = coachDao.getByID(1).first()
        // Se comprueba que el usuario es el mismo
        assertEquals(coach.user, loadedCoach.user)
    }

    @Test
    fun updateCoach() = runBlocking {
        // Se crea un coach
        val coach = CoachEntity(coachId = 1, user = user1)
        // Se inserta en la BD
        coachDao.insert(coach)
        // Se crea otro coach que copie los datos del anterior, pero cambiando los datos de usuario
        val updatedCoach = coach.copy(user = user2)
        // Se actualiza la información en la base de datos
        coachDao.update(updatedCoach)
        // Se busca por id y se comprueba que el coach es el mismo
        val loadedCoach = coachDao.getByID(1).first()
        assertEquals(updatedCoach, loadedCoach)
    }

    @Test
    fun deleteCoach() = runBlocking {
        val coach = CoachEntity(coachId = 1, user = user1)
        coachDao.insert(coach)
        // Borramos el coach insertado previamente
        coachDao.delete(coach)
        // Nos aseguramos que no hay ningún coach en la base de datos
        val loadedCoach = coachDao.getByID(1).firstOrNull()
        assertNull(loadedCoach)
    }

    @Test
    fun getByName() = runBlocking {
        // Se crean dos coaches
        val coachA = CoachEntity(coachId = 1, user = user1)
        val coachB = CoachEntity(coachId = 2, user = user2)
        // Se insertan
        coachDao.insert(coachA)
        coachDao.insert(coachB)
        // Se buscará el coachA y se comprueba que es el devuelto
        val coachesByNameA = coachDao.getByName("User1")
        assertEquals(1, coachesByNameA.first().size)
        assertTrue(coachesByNameA.first().contains(coachA))
        // Ahora se repite con el coachB
        val coachesByNameB = coachDao.getByName("User2")
        assertEquals(1, coachesByNameB.first().size)
        assertTrue(coachesByNameB.first().contains(coachB))
    }

    @Test
    fun getCoachesWithTeams() = runBlocking {
        // Se crean un par de equipos
        val team1 = TeamEntity(teamId = 1, clubId = 1, name = "Team A", category = Categories.CADETE,
            league = "1ª Oro")
        val team2 = TeamEntity(teamId = 2, clubId = 1, name = "Team B", category = Categories.JUNIOR,
            league = "2ª Plata")
        // Pondremos a los coaches a cargo de los equipos
        val coachA = CoachEntity(coachId = 1, user = user1)
        val coachB = CoachEntity(coachId = 2, user = user2)

        coachDao.insert(coachA)
        coachDao.insert(coachB)

        appDatabase.teamDao().insert(team1)
        appDatabase.teamDao().insert(team2)


        // Se asocian los coaches con los equipos asignándoles un rol
        val coachATeam1CrossRef = CoachTeamCrossRef(
            coachId = coachA.coachId,
            teamId = team1.teamId,
            role = CoachRoles.MAIN
        )
        val coachBTeam1CrossRef = CoachTeamCrossRef(
            coachId = coachB.coachId,
            teamId = team1.teamId,
            role = CoachRoles.SECOND
        )
        val coachATeam2CrossRef = CoachTeamCrossRef(
            coachId = coachA.coachId,
            teamId = team2.teamId,
            role = CoachRoles.ESPECIALIST
        )

        // Se insertan las referencias cruzadas
        appDatabase.coachTeamCrossRefDao().insert(coachATeam1CrossRef)
        appDatabase.coachTeamCrossRefDao().insert(coachATeam2CrossRef)
        appDatabase.coachTeamCrossRefDao().insert(coachBTeam1CrossRef)

        // Se comprueba que hayan dos entrenadores con equipos
        val coachesWithTeams = coachDao.getCoachesWithTeams().first()
        assertEquals(2, coachesWithTeams.size)

        // Se comprueba que el coach A tiene una relación como entrenador con los equipos
        val coachWithTeamsA = coachesWithTeams.firstOrNull { it.coach == coachA }
        assertEquals(2, coachWithTeamsA?.teams?.size)
        coachWithTeamsA?.teams?.let { assertTrue(it.contains(team1)) }
        coachWithTeamsA?.teams?.let { assertTrue(it.contains(team2)) }

        // Se comprueba que el coach B tiene una relación como entrenador con los equipos
        val coachWithTeamsB = coachesWithTeams.first { it.coach == coachB }
        assertEquals(1, coachWithTeamsB.teams.size)
        assertTrue(coachWithTeamsB.teams.contains(team1))
    }
}