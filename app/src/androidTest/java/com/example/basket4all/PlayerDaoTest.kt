package com.example.basket4all

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.basket4all.common.enums.Categories
import com.example.basket4all.data.local.AppDatabase
import com.example.basket4all.data.local.daos.PlayerDao
import com.example.basket4all.data.local.entities.PlayerEntity
import com.example.basket4all.data.local.entities.User
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

/**
 * Clase de pruebas de [PlayerDao], se realizarán tests para las siguientes funciones:
 *      - Insert
 *      - Update
 *      - Delete
 *      - GetAll
 *      - GetById
 *      - GetByName (Solo nombre y nombre-apellidos)
 *      - GetByCategory
 *      - GetPlayerAndStats
 *      - GetPlayerWithMatchStats
 */

@RunWith(AndroidJUnit4::class)
class PlayerDaoTest {
    // Declaración de variables
    private lateinit var playerDao: PlayerDao
    private lateinit var database: AppDatabase
    private lateinit var userExample: User
    private lateinit var userExample2: User

    @Before
    fun setup() {
        // Se obtiene el contexto
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Construcción de la base de datos utilizada para las pruebas
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        playerDao = database.playerDao()

        // Inicialización de un usuario de prueba
        userExample = User(
            email = "user@mail.com",
            password = "password",
            name = "Rubén",
            surname1 = "Vicente",
            surname2 = "Benito",
            birthdate = LocalDate.of(2001, 1, 9),
            picture = null
        )

        userExample2 = User(
            email = "user@mail.com",
            password = "password",
            name = "User",
            surname1 = "Usurname",
            surname2 = "Usser",
            birthdate = LocalDate.of(2007, 1, 9),
            picture = null
        )
    }

    @After
    fun tearDown() {
        // Cierre de la base de datos de las pruebas
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertPlayer() = runBlocking {
        val player = PlayerEntity(
            1,
            user = userExample,
            teamId = 1
        )
        assertEquals("0 PLAYERS IN DB ERROR", 0, playerDao.getAll().first().size)
        playerDao.insert(player)
        assertEquals("INSERT PLAYER FAILED", 1, playerDao.getAll().first().size)
    }

    @Test
    @Throws(Exception::class)
    fun updatePlayer() = runBlocking {
        val player = PlayerEntity(
            1,
            user = userExample,
            teamId = 1
        )
        playerDao.insert(player)
        var playerObtained = playerDao.getByID(1).first()
        assertEquals("PLAYER OBTAINED FAILED", player, playerObtained)
        val playerUpdated = PlayerEntity(
            id = 1,
            user = userExample,
            teamId = 2
        )
        playerDao.update(playerUpdated)
        playerObtained = playerDao.getByID(1).first()
        assertEquals("PLAYER UPDATED OBTAINED FAILED", playerUpdated, playerObtained)
    }

    @Test
    @Throws(Exception::class)
    fun deletePlayer() = runBlocking {
        val player = PlayerEntity(
            1,
            user = userExample,
            teamId = 1
        )
        playerDao.insert(player)
        playerDao.delete(player)
        val playerObtained = playerDao.getByID(1).first()
        assertNull("DELETE PLAYER FAILED", playerObtained)
    }

    @Test
    @Throws(Exception::class)
    fun getByName() = runBlocking{
        val player = PlayerEntity(
            1,
            user = userExample,
            teamId = 1
        )
        val player2 = PlayerEntity(
            2,
            user = userExample2,
            teamId = 1
        )
        playerDao.insert(player)
        playerDao.insert(player2)

        val playersObtained = playerDao.getByName("Rubén").first().first()
        assertEquals("PLAYER OBTAINED BY NAME FAILED", player, playersObtained)
        val playerObtained = playerDao.getByName("Rubén", "Vicente", "Benito").first()
        assertEquals("PLAYER OBTAINED BY FULL NAME FAILED", player, playerObtained)
    }

    @Test
    @Throws(Exception::class)
    fun getByCategory() = runBlocking {
        val player = PlayerEntity(
            1,
            user = userExample,
            teamId = 1
        )
        val player2 = PlayerEntity(
            2,
            user = userExample2,
            teamId = 1
        )

        playerDao.insert(player)
        playerDao.insert(player2)

        val seniorPlayers = playerDao.getByCategory(Categories.SENIOR).first()
        val sub22Players = playerDao.getByCategory(Categories.SUB22).first()
        val juniorPlayers = playerDao.getByCategory(Categories.JUNIOR).first()
        val cadetePlayers = playerDao.getByCategory(Categories.CADETE).first()
        val alevinPlayers = playerDao.getByCategory(Categories.ALEVIN).first()
        val benjaminPlayers = playerDao.getByCategory(Categories.BENJAMIN).first()
        val prebenjaminPlayers = playerDao.getByCategory(Categories.PREBENJAMIN).first()
        val babyPlayers = playerDao.getByCategory(Categories.BABYBASKET).first()

        assertEquals(emptyList<PlayerEntity> (), sub22Players)
        assertEquals(emptyList<PlayerEntity> (), cadetePlayers)
        assertEquals(emptyList<PlayerEntity> (), alevinPlayers)
        assertEquals(emptyList<PlayerEntity> (), benjaminPlayers)
        assertEquals(emptyList<PlayerEntity> (), prebenjaminPlayers)
        assertEquals(emptyList<PlayerEntity> (), babyPlayers)
        assertEquals("SENIORS OBTAINED FAILED", player, seniorPlayers.first())
        assertEquals("CADETES OBTAINED FAILED", player2, juniorPlayers.first())
    }

    @Test
    @Throws(Exception::class)
    fun getPlayersAndStats() = runBlocking {

    }

    @Test
    @Throws(Exception::class)
    fun getPlayerWithMatchStats() = runBlocking {

    }
}