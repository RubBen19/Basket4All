package com.example.basket4all

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.basket4all.data.local.AppDatabase
import com.example.basket4all.data.local.daos.TeamStatsDao
import com.example.basket4all.data.local.entities.TeamStats
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Clase de pruebas de [TeamStatsDao], se realizarán tests para las siguientes funciones:
 *      - Insert
 *      - Update
 *      - Delete
 *      - GetAll
 *      - GetById
 *      - GetByTeam
 */

@RunWith(AndroidJUnit4::class)
class TeamStatsDaoTest {
    //Declaración de variables
    private lateinit var teamStatsDao: TeamStatsDao
    private lateinit var database: AppDatabase

    @Before
    fun setup() {
        //Se obtiene el contexto
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        //Construcción de la base de datos
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        //Inicio de la variable teamStatsDao con el Dao de TeamStats
        teamStatsDao = database.teamStatsDao()
    }

    @After
    fun tearDown() {
        //Cierre de la base de datos de las pruebas
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertTeamStat() = runBlocking {
        //Creación de una estadística de equipo
        val stats = TeamStats(1,1)
        //Comprobación de que la base de datos esta vacía
        assertEquals("O TEAMS STATS IN DB ERROR", 0,
            teamStatsDao.getAll().first().size)
        //Inserción de la estadística de prueba
        teamStatsDao.insert(stats)
        //Se comprueba que el elemento insertado es el que debería ser
        assertEquals("UNKNOWN TEAM ERROR", stats,
            teamStatsDao.getAll().first().first())
    }

    @Test
    @Throws(Exception::class)
    fun updateTeamStat() = runBlocking {
        val stats = TeamStats(1,1)
        teamStatsDao.insert(stats)
        //Obtención de una estadistica de equipo por su id
        val statObt = teamStatsDao.getByID(1).first()
        TestCase.assertEquals("STATS OBTAINED FAILED", stats, statObt)
        //Modificación del las estadísticas
        val statsUpdated = TeamStats(1,2)
        //Se actualiza la modificación en la base de datos
        teamStatsDao.update(statsUpdated)
        //Se comprueba que la modificación se ha realizado
        assertEquals("ISN'T THE SAME", statsUpdated,
            teamStatsDao.getByID(1).first())
    }

    @Test
    @Throws(Exception::class)
    fun deleteTeamStat() = runBlocking {
        val stats = TeamStats(1,1)
        teamStatsDao.insert(stats)
        //Se elimina el elemento insertado
        teamStatsDao.delete(stats)
        //Como solo había un elemento, se comprueba que ya no hay estadísticas de equipo en la BD
        assertEquals("STATS DELETE FAILED", 0,
            teamStatsDao.getAll().first().size)
    }

    @Test
    @Throws(Exception::class)
    fun getByTeam() = runBlocking {
        val stats = TeamStats(1,1)
        val stats2 = TeamStats(3,2)
        teamStatsDao.insert(stats)
        teamStatsDao.insert(stats2)
        //Obtención de las estadisticas por el equipo al que pertenecen
        val s1 = teamStatsDao.getByTeam(1).first()
        val s2 = teamStatsDao.getByTeam(2).first()
        //Comprobación de resultados
        assertEquals(stats, s1)
        assertEquals(stats2, s2)
    }
}