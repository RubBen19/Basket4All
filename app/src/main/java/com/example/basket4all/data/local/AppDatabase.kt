package com.example.basket4all.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.basket4all.common.enums.Categories
import com.example.basket4all.data.local.daos.ClubDao
import com.example.basket4all.data.local.daos.CoachDao
import com.example.basket4all.data.local.daos.MatchDao
import com.example.basket4all.data.local.daos.MatchStatsDao
import com.example.basket4all.data.local.daos.PlayerDao
import com.example.basket4all.data.local.daos.PlayerStatsDao
import com.example.basket4all.data.local.daos.TeamDao
import com.example.basket4all.data.local.daos.TeamStatsDao
import com.example.basket4all.data.local.entities.ClubEntity
import com.example.basket4all.data.local.entities.CoachEntity
import com.example.basket4all.data.local.entities.MatchEntity
import com.example.basket4all.data.local.entities.MatchStats
import com.example.basket4all.data.local.entities.PlayerEntity
import com.example.basket4all.data.local.entities.PlayerStats
import com.example.basket4all.data.local.entities.TeamEntity
import com.example.basket4all.data.local.entities.TeamStats
import com.example.basket4all.data.local.entities.User
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate

@Database(
    entities = [PlayerEntity::class, CoachEntity::class, TeamEntity::class, ClubEntity::class,
        MatchEntity::class, PlayerStats::class, MatchStats::class, TeamStats::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun clubDao(): ClubDao
    abstract fun coachDao(): CoachDao
    abstract fun matchDao(): MatchDao
    abstract fun matchStatsDao(): MatchStatsDao
    abstract fun playerDao(): PlayerDao
    abstract fun playerStatsDao(): PlayerStatsDao
    abstract fun teamDao(): TeamDao
    abstract fun teamStatsDao(): TeamStatsDao

    companion object {
        // Constructor Singleton para evitar múltiples instancias de la base de datos
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "b4all_database"
                )
                    .addCallback(appCallback)
                    .build()
                INSTANCE = instance
                return instance
            }
        }

        @OptIn(DelicateCoroutinesApi::class)
        private val appCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                GlobalScope.launch(Dispatchers.IO) {
                    insertInitialData()
                }
            }
        }

        private suspend fun insertInitialData() {
            // Creación de clubes
            val club1 = ClubEntity(
                name = "Club 1",
                description = "Club 1"
            )
            // Creación de equipos
            val team1 = TeamEntity(
                clubId = club1.id,
                name = "Team 1",
                category = Categories.SENIOR,
                league = "2ª Oro"
            )
            // Some players insertion
            val user1 = User(
                email = "vicentebenitoruben@gmail.com",
                password = "password",
                name = "Rubén",
                surname1 = "Vicente",
                surname2 = "Benito",
                birthdate = LocalDate.of(2001, 1, 9),
                picture = 0
            )

            val user2 = User(
                email = "ismael@email.com",
                password = "password",
                name = "Ismael",
                surname1 = "Navarro",
                surname2 = "Macias",
                birthdate = LocalDate.of(2000, 1, 1),
                picture = 1
            )

            val user3 = User(
                email = "samuel@gmail.com",
                password = "password",
                name = "Samuel",
                surname1 = "Gordillo",
                surname2 = "Perez",
                birthdate = LocalDate.of(2000, 1, 1),
                picture = 2
            )

            val user4 = User(
                email = "david@email.com",
                password = "password",
                name = "David",
                surname1 = "Garcia",
                surname2 = "Mayordomo",
                birthdate = LocalDate.of(2000, 1, 1),
                picture = 3
            )

            val user5 = User(
                email = "gonza@email.com",
                password = "password",
                name = "Gonzalo",
                surname1 = "Crespo",
                surname2 = "Dominguez",
                birthdate = LocalDate.of(2000, 1, 1),
                picture = 4
            )
            // Agrupación en listas de los distintos elementos
            val clubs = listOf(club1)
            val teams = listOf(team1)
            val players = listOf(user1, user2, user3, user4, user5)
            val coaches = listOf(user1)
            // Insercción de clubes y equipos
            insertClubs(clubs)
            insertTeams(teams)
            // Insercción de los jugadores y entrenadores del team1
            insertPlayers(players, team1)
            insertCoaches(coaches, team1)

        }

        private suspend fun insertPlayers(users: List<User>, team: TeamEntity) {
            for (user in users) {
                val player = PlayerEntity (
                    user = user,
                    teamId = team.id
                )
                INSTANCE?.playerDao()?.insert(player)
            }
        }

        private suspend fun insertCoaches(users: List<User>, team: TeamEntity) {
            for (user in users) {
                val coach = CoachEntity (
                    user = user,
                    teamId = listOf(team.id)
                )
                INSTANCE?.coachDao()?.insert(coach)
            }
        }

        private suspend fun insertClubs(clubs: List<ClubEntity>) {
            for (club in clubs) {
                INSTANCE?.clubDao()?.insert(club)
            }
        }

        private suspend fun insertTeams(teams: List<TeamEntity>) {
            for (team in teams) {
                INSTANCE?.teamDao()?.insert(team)
            }
        }
    }

}