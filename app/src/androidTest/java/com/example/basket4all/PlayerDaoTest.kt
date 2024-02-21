package com.example.basket4all

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.basket4all.data.local.AppDatabase
import com.example.basket4all.data.local.daos.PlayerDao
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

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

    @Before
    fun setup() {
        // Se obtiene el contexto
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Construcción de la base de datos utilizada para las pruebas
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        playerDao = database.playerDao()
    }

    @After
    fun tearDown() {
        // Cierre de la base de datos de las pruebas
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertPlayer() {

    }

    @Test
    @Throws(Exception::class)
    fun updatePlayer() {

    }

    @Test
    @Throws(Exception::class)
    fun deletePlayer() {

    }

    @Test
    @Throws(Exception::class)
    fun getPlayers() {

    }

    @Test
    @Throws(Exception::class)
    fun getPlayerByID() {

    }

    @Test
    @Throws(Exception::class)
    fun getByName() {

    }

    @Test
    @Throws(Exception::class)
    fun getByCategory() {

    }

    @Test
    @Throws(Exception::class)
    fun getPlayersAndStats() {

    }

    @Test
    @Throws(Exception::class)
    fun getPlayerWithMatchStats() {

    }
}