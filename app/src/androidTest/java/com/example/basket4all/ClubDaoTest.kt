package com.example.basket4all

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.basket4all.common.enums.Categories
import com.example.basket4all.data.local.AppDatabase
import com.example.basket4all.data.local.daos.ClubDao
import com.example.basket4all.data.local.entities.ClubEntity
import com.example.basket4all.data.local.entities.TeamEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Clase de pruebas de [ClubDao], se realizarán tests para las siguientes funciones:
 *      - Insert
 *      - Update
 *      - Delete
 *      - GetAll
 *      - GetById
 *      - GetByName
 *      - GetClubsWithTeams
 */
@RunWith(AndroidJUnit4::class)
class ClubDaoTest {
    //Declaración de variables
    private lateinit var clubDao: ClubDao
    private lateinit var appDatabase: AppDatabase

    @Before
    fun setup() {
        //Se obtiene el contexto
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        //Construcción de la base de datos
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        //Inicio de la variable clubDao con el Dao de Club
        clubDao = appDatabase.clubDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        //Cierre de la base de datos de las pruebas
        appDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertClub() = runBlocking {
        //Creación de un club
        val club = ClubEntity(id = 1, name = "Club A", description = "")
        //Se inserta
        clubDao.insert(club)
        //Se obtiene el club desde la base de datos
        val loadedClub = clubDao.getAll().first().first()
        //Se comprueba que es el mismo club
        assertEquals(club, loadedClub)
    }

    @Test
    @Throws(Exception::class)
    fun updateClub() = runBlocking {
        val club = ClubEntity(id = 1, name = "Club A", description = "")
        clubDao.insert(club)
        //Se actualiza el valor del nombre del club
        val updatedClub = club.copy(name = "Updated Club A")
        clubDao.update(updatedClub)
        //Se recupera el club desde la BD y se comprueba que se haya realizado la modificación
        val loadedClub = clubDao.getAll().first().first()
        assertEquals(updatedClub, loadedClub)
    }

    @Test
    @Throws(Exception::class)
    fun deleteClub() = runBlocking {
        val club = ClubEntity(id = 1, name = "Club A", description = "")
        clubDao.insert(club)
        //Se elimina el club
        clubDao.delete(club)
        //Se obtienen los clubes
        val loadedClub = clubDao.getAll().first()
        //Se comprueba que no esta vacía
        assertEquals(true, loadedClub.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun getByName() = runBlocking {
        //Creación de 2 clubes
        val clubA = ClubEntity(id = 1, name = "Club A", description = "")
        val clubB = ClubEntity(id = 2, name = "Club B", description = "")
        //Se insertan
        clubDao.insert(clubA)
        clubDao.insert(clubB)
        //Se comprueba que al pedir clubes que empiecen por Club devuelve a los 2
        var clubsByName = clubDao.getByName("Club")
        assertEquals(2, clubsByName.first().size)
        assertTrue(clubsByName.first().contains(clubA))
        assertTrue(clubsByName.first().contains(clubB))
        //Se comprueba que se devuelve el club A
        clubsByName = clubDao.getByName("Club A")
        assertEquals(1, clubsByName.first().size)
        assertTrue(clubsByName.first().first() == clubA)
        //Se comprueba que se devuelve el club B
        clubsByName = clubDao.getByName("Club B")
        assertEquals(1, clubsByName.first().size)
        assertTrue(clubsByName.first().first() == clubB)
    }

    @Test
    @Throws(Exception::class)
    fun getClubsWithTeams() = runBlocking {
        //Se crean 3 clubes
        val clubA = ClubEntity(id = 1, name = "Club A", description = "")
        val clubB = ClubEntity(id = 2, name = "Club B", description = "")
        val clubC = ClubEntity(id = 3, name = "Club C", description = "")
        clubDao.insert(clubA)
        clubDao.insert(clubB)
        clubDao.insert(clubC)
        //Prueba con clubes con equipos
        val team1 = TeamEntity(teamId = 1, clubId = clubA.id, name = "Team A",
            category = Categories.CADETE, league =  "1ª Oro")
        val team2 = TeamEntity(teamId = 2, clubId = clubA.id, name = "Team A",
            category = Categories.JUNIOR, league =  "2ª Plata")
        val team3 = TeamEntity(teamId = 3, clubId = clubB.id, name = "Team B",
            category = Categories.CADETE, league =  "2ª Oro")
        //Inserción de equipos
        val teamDao = appDatabase.teamDao()
        teamDao.insert(team1)
        teamDao.insert(team2)
        teamDao.insert(team3)
        //Se obtienen los clubes que tienen algún equipo
        val clubsWithTeams = clubDao.getClubsWithTeams().first()
        //Se comprueba que solo son 2
        assertEquals(2, clubsWithTeams.size)
        //Se verifica que esta A junto a sus equipos
        val teamsOfClubA = clubsWithTeams.first { it.club == clubA }
        assertEquals(2, teamsOfClubA.teams.size)
        assertTrue(teamsOfClubA.teams.contains(team1))
        assertTrue(teamsOfClubA.teams.contains(team2))
        //Se verifica que esta B junto a su equipo
        val teamsOfClubB = clubsWithTeams.first { it.club == clubB }
        assertEquals(1, teamsOfClubB.teams.size)
        assertTrue(teamsOfClubB.teams.contains(team3))
    }

}
