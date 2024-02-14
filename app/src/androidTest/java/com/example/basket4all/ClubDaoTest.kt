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
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ClubDaoTest {

    private lateinit var clubDao: ClubDao
    private lateinit var appDatabase: AppDatabase

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        clubDao = appDatabase.clubDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertClub() = runBlocking {
        val club = ClubEntity(id = 1, name = "Club A", description = "")
        clubDao.insert(club)
        val loadedClub = clubDao.getAll().first().first()
        assertEquals(club, loadedClub)
    }

    @Test
    @Throws(Exception::class)
    fun updateClub() = runBlocking {
        val club = ClubEntity(id = 1, name = "Club A", description = "")
        clubDao.insert(club)

        val updatedClub = club.copy(name = "Updated Club A")
        clubDao.update(updatedClub)

        val loadedClub = clubDao.getAll().first().first()
        assertEquals(updatedClub, loadedClub)
    }

    @Test
    @Throws(Exception::class)
    fun deleteClub() = runBlocking {
        val club = ClubEntity(id = 1, name = "Club A", description = "")
        clubDao.insert(club)

        clubDao.delete(club)

        val loadedClub = clubDao.getAll().first()
        assertEquals(true, loadedClub.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun getByName() = runBlocking {
        val clubA = ClubEntity(id = 1, name = "Club A", description = "")
        val clubB = ClubEntity(id = 2, name = "Club B", description = "")

        clubDao.insert(clubA)
        clubDao.insert(clubB)

        var clubsByName = clubDao.getByName("Club")
        assertEquals(2, clubsByName.first().size)
        assertTrue(clubsByName.first().contains(clubA))
        assertTrue(clubsByName.first().contains(clubB))

        clubsByName = clubDao.getByName("Club A")
        assertEquals(1, clubsByName.first().size)
        assertTrue(clubsByName.first().first() == clubA)

        clubsByName = clubDao.getByName("Club B")
        assertEquals(1, clubsByName.first().size)
        assertTrue(clubsByName.first().first() == clubB)
    }

    @Test
    @Throws(Exception::class)
    fun getClubsWithTeams() = runBlocking {
        val clubA = ClubEntity(id = 1, name = "Club A", description = "")
        val clubB = ClubEntity(id = 2, name = "Club B", description = "")

        clubDao.insert(clubA)
        clubDao.insert(clubB)

        // Prueba con clubes con equipos
        val team1 = TeamEntity(teamId = 1, clubId = clubA.id, name = "Team A",
            category = Categories.CADETE, league =  "1ª Oro")
        val team2 = TeamEntity(teamId = 2, clubId = clubA.id, name = "Team A",
            category = Categories.JUNIOR, league =  "2ª Plata")
        val team3 = TeamEntity(teamId = 3, clubId = clubB.id, name = "Team B",
            category = Categories.CADETE, league =  "2ª Oro")

        // Insercción de equipos
        val teamDao = appDatabase.teamDao()
        teamDao.insert(team1)
        teamDao.insert(team2)
        teamDao.insert(team3)

        val clubsWithTeams = clubDao.getClubsWithTeams().first()
        assertEquals(2, clubsWithTeams.size)

        val teamsOfClubA = clubsWithTeams.first { it.club == clubA }
        assertEquals(2, teamsOfClubA.teams.size)
        assertTrue(teamsOfClubA.teams.contains(team1))
        assertTrue(teamsOfClubA.teams.contains(team2))

        val teamsOfClubB = clubsWithTeams.first { it.club == clubB }
        assertEquals(1, teamsOfClubB.teams.size)
        assertTrue(teamsOfClubB.teams.contains(team3))
    }

}
