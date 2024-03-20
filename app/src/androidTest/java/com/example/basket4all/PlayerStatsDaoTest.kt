package com.example.basket4all

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.basket4all.data.local.AppDatabase
import com.example.basket4all.data.local.daos.PlayerDao
import com.example.basket4all.data.local.daos.PlayerStatsDao
import com.example.basket4all.data.local.entities.PlayerStats
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Clase de pruebas de [PlayerStatsDao], se realizarán tests para las siguientes funciones:
 *      - Insert
 *      - Update
 *      - Delete
 *      - GetAll
 *      - GetById
 *      - GetByPlayer
 */
@RunWith(AndroidJUnit4::class)
class PlayerStatsDaoTest {
    //Declaración de variables
    private lateinit var playerStatsDao: PlayerStatsDao
    private lateinit var database: AppDatabase

    @Before
    fun setup() {
        //Se obtiene el contexto
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        //Construcción de la base de datos
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        //Inicio de la variable del Dao de PlayerStats
        playerStatsDao = database.playerStatsDao()
    }

    @After
    fun tearDown() {
        //Cierre de la base de datos de las pruebas
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertPlayerStats() = runBlocking {
        //Creación de unas estadísticas de prueba
        val playerStats = PlayerStats(1,1)
        //Comprobación de que la base de datos esta vacía
        assertEquals("O STATS IN DB ERROR", 0, playerStatsDao.getAll().first().size)
        //Inserción de las estadísticas de prueba
        playerStatsDao.insert(playerStats)
        //Obtención de todas las estadísticas
        val stats = playerStatsDao.getAll().first()
        //Comprobación de ha sido insertado algún elemento
        assertEquals("INSERT STATS FAILED", 1, stats.size)
        //Se comprueba que el elemento insertado es el que debería ser
        assertEquals("UNKNOWN STATS ERROR", playerStats, stats.first())
    }

    @Test
    @Throws(Exception::class)
    fun deletePlayerStats() = runBlocking {
        val playerStats = PlayerStats(1,1)
        playerStatsDao.insert(playerStats)
        //Se elimina el elemento insertado
        playerStatsDao.delete(playerStats)
        //Como solo había un elemento, se comprueba que ya no hay estadísticas de jugador en la BD
        assertEquals("STATS DELETE FAILED", 0, playerStatsDao.getAll().first().size)
    }

    @Test
    @Throws(Exception::class)
    fun updatePlayerStats() = runBlocking {
        val playerStats = PlayerStats(1,1)
        playerStatsDao.insert(playerStats)
        //Obtención de las estadísticas por su id (1 en este caso)
        var statsObt = playerStatsDao.getByID(1).first()
        //Se asegura que el elemento obtenido por el id es el correcto
        assertEquals("STATS OBTAINED FAILED", playerStats, statsObt)
        //Modificación del jugador que tendrá estas estadísticas
        val playerStatsUpdated = PlayerStats(1, 2)
        //Se actualiza la base de datos con la modificación
        playerStatsDao.update(playerStatsUpdated)
        statsObt = playerStatsDao.getByID(1).first()
        //Se comprueba que la modificación se ha realizado correctamente
        assertEquals("ISN'T THE SAME", playerStatsUpdated, statsObt)
    }

    @Test
    @Throws(Exception::class)
    fun getByPlayer() = runBlocking {
        //Creación de estadisticas para varios jugadores
        val playerStats = PlayerStats(playerId = 1)
        val playerStats2 = PlayerStats(playerId = 3)
        val playerStats3 = PlayerStats(playerId = 2)
        //Se insertan
        playerStatsDao.insert(playerStats)
        playerStatsDao.insert(playerStats2)
        playerStatsDao.insert(playerStats3)
        //Se obtienen todas las estadisticas por el id del jugador
        val statsObt = playerStatsDao.getByPlayer(1).first().first()
        val stats2Obt = playerStatsDao.getByPlayer(3).first().first()
        val stats3Obt = playerStatsDao.getByPlayer(2).first().first()
        //Se comprueba que las estadísticas obtenidas son las correctas
        assertEquals("STATS 1 OBTAINED ERROR", playerStats.playerId, statsObt.playerId)
        assertEquals("STATS 2 OBTAINED ERROR", playerStats2.playerId, stats2Obt.playerId)
        assertEquals("STATS 3 OBTAINED ERROR", playerStats3.playerId, stats3Obt.playerId)
    }
}