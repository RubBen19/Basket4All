package com.example.basket4all.data.local

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.basket4all.common.classes.Score
import com.example.basket4all.common.enums.Categories
import com.example.basket4all.common.enums.CoachRoles
import com.example.basket4all.common.enums.PlayerPositions
import com.example.basket4all.data.local.daos.ClubDao
import com.example.basket4all.data.local.daos.CoachDao
import com.example.basket4all.data.local.daos.CoachTeamCrossRefDao
import com.example.basket4all.data.local.daos.MatchDao
import com.example.basket4all.data.local.daos.MatchStatsDao
import com.example.basket4all.data.local.daos.PlayerDao
import com.example.basket4all.data.local.daos.PlayerStatsDao
import com.example.basket4all.data.local.daos.TeamDao
import com.example.basket4all.data.local.daos.TeamStatsDao
import com.example.basket4all.data.local.entities.ClubEntity
import com.example.basket4all.data.local.entities.CoachEntity
import com.example.basket4all.data.local.entities.CoachTeamCrossRef
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate

@Database(
    entities = [PlayerEntity::class, CoachEntity::class, TeamEntity::class, ClubEntity::class,
        MatchEntity::class, PlayerStats::class, MatchStats::class, TeamStats::class,
        CoachTeamCrossRef::class],
    version = 2,
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

    abstract fun coachTeamCrossRefDao(): CoachTeamCrossRefDao

    companion object {
        // Constructor Singleton para evitar múltiples instancias de la base de datos
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            Log.d("DB", "Getting database")
            return INSTANCE ?: synchronized(this) {
                Log.d("DB", "Building database")
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "b4all_database"
                )
                    .addCallback(appCallback)
                    .build()
                Log.d("DB", "Database built")
                INSTANCE = instance
                return instance
            }
        }

        @OptIn(DelicateCoroutinesApi::class)
        private val appCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Log.d("DB", "Insert initial data starting")
                GlobalScope.launch(Dispatchers.IO) {
                    insertInitialData()
                }
                Log.d("DB", "Insert initial data finished")
            }
        }

        private suspend fun insertInitialData() {
            // Creación de clubes
            val clubesNames: Array<String> = arrayOf(
                "BASKET HOLOS SALESIANOS",
                "MEJORADA 2012",
                "SPARTAN SF C.D.E.",
                "REFORDOMUS GRIÑON",
                "PUREZA DE MARÍA",
                "CB VILLA DE LEGANÉS",
            )
            val clubes: MutableList<ClubEntity> = mutableListOf()
            var id = 1

            for (names in clubesNames) {
                clubes.add(ClubEntity(id, names, ""))
                id++
            }

            // Creación de equipos (2ª Autonómica ORO)
            val teamsNames: Array<String> = arrayOf(
                "CB VILLA DE LEGANÉS",
                "BASKET HOLOS SALESIANOS",
                "MEJORADA 2012",
                "SPARTAN SF C.D.E.",
                "REFORDOMUS GRIÑON",
                "PUREZA DE MARÍA"
            )
            val teams: MutableList<TeamEntity> = mutableListOf()
            id = 1
            for (name in teamsNames) {
                teams.add(
                    TeamEntity(
                        clubId = id,
                        teamId = id,
                        name =  name,
                        category = Categories.SENIOR,
                        league = "2ª Autonómica ORO"
                    )
                )
                id++
            }

            // Insercción de partidos
            val matches: MutableList<MatchEntity> = mutableListOf()
            val match1 = MatchEntity(
                1,
                6,
                1,
                LocalDate.of(2023, 10, 8),
                Score(67, 47)
            )
            matches.add(match1)

            val match2 = MatchEntity(
                2,
                1,
                5,
                LocalDate.of(2023, 10, 15),
                Score(68, 65)
            )
            matches.add(match2)

            val match3 = MatchEntity(
                3,
                4,
                1,
                LocalDate.of(2023, 10, 22),
                Score(85, 80)
            )
            matches.add(match3)

            val match4 = MatchEntity(
                4,
                1,
                3,
                LocalDate.of(2023, 10, 29),
                Score(48, 65)
            )
            matches.add(match4)

            val match5 = MatchEntity(
                5,
                5,
                4,
                LocalDate.of(2023, 10, 29),
                Score(51, 73)
            )
            matches.add(match5)

            val match6 = MatchEntity(
                6,
                3,
                5,
                LocalDate.of(2023, 11, 5),
                Score(68, 58)
            )
            matches.add(match6)

            val match7 = MatchEntity(
                7,
                6,
                4,
                LocalDate.of(2023, 11, 5),
                Score(64, 76)
            )
            matches.add(match7)

            val match8 = MatchEntity(
                8,
                2,
                1,
                LocalDate.of(2023, 11, 5),
                Score(69, 53)
            )
            matches.add(match8)

            val match9 = MatchEntity(
                9,
                4,
                3,
                LocalDate.of(2023, 11, 12),
                Score(74, 57)
            )
            matches.add(match9)

            val match10 = MatchEntity(
                10,
                5,
                2,
                LocalDate.of(2023, 11, 12),
                Score(66, 70)
            )
            matches.add(match10)

            val match11 = MatchEntity(
                11,
                6,
                3,
                LocalDate.of(2023, 11, 19),
                Score(80, 55)
            )
            matches.add(match11)

            val match12 = MatchEntity(
                12,
                2,
                4,
                LocalDate.of(2023, 11, 19),
                Score(63, 73)
            )
            matches.add(match12)

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
            val players = listOf(user1, user2, user3, user4, user5)
            val coaches = listOf(user1)
            // Insercción de clubes y equipos
            insertClubs(clubes)
            insertTeams(teams)
            // Insercción de partidos
            insertMatches(matches)
            // Insercción de los jugadores y entrenadores del villa de leganés
            insertPlayers(players, teams[0])
            insertCoaches(coaches, teams[0])
        }

        private suspend fun insertMatches(matches: List<MatchEntity>) {
            for (match in matches) {
                INSTANCE?.matchDao()?.insert(match)

                val localT = INSTANCE?.teamStatsDao()?.getByTeam(match.localTeamId)?.first()
                localT?.matchPlayed = localT?.matchPlayed!! + 1
                if (match.score.getLocalScore() > match.score.getVisitorScore()) localT.wins += 1
                localT.points += match.score.getLocalScore()
                val visitorT = INSTANCE?.teamStatsDao()?.getByTeam(match.visitorTeamId)?.first()
                visitorT?.matchPlayed = visitorT?.matchPlayed!! + 1
                if (match.score.getLocalScore() < match.score.getVisitorScore()) visitorT.wins += 1
                visitorT.points += match.score.getVisitorScore()

                INSTANCE?.teamStatsDao()?.update(localT)
                INSTANCE?.teamStatsDao()?.update(visitorT)
            }
        }

        private suspend fun insertPlayers(users: List<User>, team: TeamEntity) {
            val list = mutableListOf(PlayerPositions.PIVOT, PlayerPositions.ALAPIVOT)
            var id = 1
            for (user in users) {
                val player = PlayerEntity (
                    id = id,
                    user = user,
                    teamId = team.teamId,
                    number = if (user.name == "Rubén") 91 else 1,
                    positions =  if (user.name == "Rubén") list else mutableListOf()
                )
                val stats = PlayerStats(
                    playerId = player.id,
                )
                INSTANCE?.playerDao()?.insert(player)
                INSTANCE?.playerStatsDao()?.insert(stats)
                id++
            }
        }

        private suspend fun insertCoaches(users: List<User>, team: TeamEntity) {
            for (user in users) {
                val coach = CoachEntity (
                    coachId = 1,
                    user = user
                )
                val coachTeamCrossRef = CoachTeamCrossRef(
                    coachId = coach.coachId,
                    teamId = team.teamId,
                    role = CoachRoles.MAIN
                )
                INSTANCE?.coachDao()?.insert(coach)
                INSTANCE?.coachTeamCrossRefDao()?.insert(coachTeamCrossRef)
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
                INSTANCE?.teamStatsDao()?.insert(
                    TeamStats(teamId = team.teamId)
                )
            }
        }
    }

}