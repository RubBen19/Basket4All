package com.example.basket4all.presentation.navigation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.basket4all.common.messengers.SessionManager
import com.example.basket4all.common.elements.B4AllNavigationBar
import com.example.basket4all.data.local.AppDatabase
import com.example.basket4all.presentation.screens.AddPlayerStatsScreen
import com.example.basket4all.presentation.screens.CalendarScreen
import com.example.basket4all.presentation.screens.CoachModeScreen
import com.example.basket4all.presentation.screens.EditUserScreen
import com.example.basket4all.presentation.screens.HomeScreen
import com.example.basket4all.presentation.screens.LogScreen
import com.example.basket4all.presentation.screens.MatchesScreen
import com.example.basket4all.presentation.screens.NewMatchScreen
import com.example.basket4all.presentation.screens.ProfileScreen
import com.example.basket4all.presentation.screens.RegisterScreen
import com.example.basket4all.presentation.screens.SecondScreen
import com.example.basket4all.presentation.screens.SplashScreen
import com.example.basket4all.presentation.screens.TacticsScreen
import com.example.basket4all.presentation.screens.TeamScreen
import com.example.basket4all.presentation.screens.TeamStatsScreen
import com.example.basket4all.presentation.viewmodels.db.CalendarEventViewModel
import com.example.basket4all.presentation.viewmodels.db.CalendarEventViewModelFactory
import com.example.basket4all.presentation.viewmodels.db.CoachesViewModel
import com.example.basket4all.presentation.viewmodels.db.CoachesViewModelFactory
import com.example.basket4all.presentation.viewmodels.db.MatchesViewModel
import com.example.basket4all.presentation.viewmodels.db.MatchesViewModelFactory
import com.example.basket4all.presentation.viewmodels.db.PlayerStatsViewModel
import com.example.basket4all.presentation.viewmodels.db.PlayerStatsViewModelFactory
import com.example.basket4all.presentation.viewmodels.screens.LoginViewModel
import com.example.basket4all.presentation.viewmodels.screens.LoginViewModelFactory
import com.example.basket4all.presentation.viewmodels.db.PlayersViewModel
import com.example.basket4all.presentation.viewmodels.db.PlayersViewModelFactory
import com.example.basket4all.presentation.viewmodels.db.TeamStatsViewModel
import com.example.basket4all.presentation.viewmodels.db.TeamStatsViewModelFactory
import com.example.basket4all.presentation.viewmodels.db.TeamViewModel
import com.example.basket4all.presentation.viewmodels.db.TeamViewModelFactory
import com.example.basket4all.board_tool.BoardScreen
import com.example.basket4all.presentation.screens.ClassificationScreen
import com.example.basket4all.presentation.screens.MatchScreen
import com.example.basket4all.presentation.screens.MatchStatsScreen
import com.example.basket4all.presentation.screens.MiLigueScreen
import com.example.basket4all.presentation.screens.MyPasswordScreen
import com.example.basket4all.presentation.screens.NewResultScreen
import com.example.basket4all.presentation.screens.PlayerStatsScreen
import com.example.basket4all.presentation.screens.ShotInformScreen
import com.example.basket4all.presentation.viewmodels.db.MatchStatsViewModel
import com.example.basket4all.presentation.viewmodels.db.MatchStatsViewModelFactory

/**
 * ARCHIVO: AppNavigation.kt
 * FUNCIÓN: El objetivo de este archivo es mantener la navegación de la app
 */
@Composable
fun AppNavigation() {
    // Recuerdo y obtengo el controlador de navegación
    val navController = rememberNavController()
    
    // Esta variable indicará si la barra de navegación tiene que mostrarse o no en la screen
    var navIsVisible by remember {mutableStateOf (false)}

    // Administrador de sesión
    val sessionManager = SessionManager.getInstance()

    //Room y DAO
    val appDatabase = AppDatabase.getDatabase(context = LocalContext.current.applicationContext)
    val playerDao = appDatabase.playerDao()
    val coachDao = appDatabase.coachDao()
    val teamDao = appDatabase.teamDao()
    val teamStatsDao = appDatabase.teamStatsDao()
    val matchDao = appDatabase.matchDao()
    val playerStatsDao = appDatabase.playerStatsDao()
    val calendarEventDao = appDatabase.calendarEventDao()
    val matchStatsDao = appDatabase.matchStatsDao()

    //ViewModels relacionados con la base de datos
    val playersViewModel: PlayersViewModel = viewModel(factory = PlayersViewModelFactory(playerDao))
    val coachesViewModel: CoachesViewModel = viewModel(factory = CoachesViewModelFactory(coachDao))
    val teamsViewModel: TeamViewModel = viewModel(factory = TeamViewModelFactory(teamDao))
    val teamStatsViewModel: TeamStatsViewModel = viewModel(
        factory = TeamStatsViewModelFactory(teamStatsDao)
    )
    val matchesViewModel: MatchesViewModel = viewModel(factory = MatchesViewModelFactory(matchDao))
    val playerStatsViewModel: PlayerStatsViewModel = viewModel(
        factory = PlayerStatsViewModelFactory(playerStatsDao)
    )
    val calendarEventViewModel: CalendarEventViewModel = viewModel(
        factory = CalendarEventViewModelFactory(calendarEventDao)
    )
    val matchStatsViewModel: MatchStatsViewModel = viewModel(
        factory = MatchStatsViewModelFactory(matchStatsDao)
    )

    //ViewModels relacionados con screens
    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(playersViewModel, coachesViewModel)
    )

    //Con NavHost almaceno y gestiono las pantallas
    NavHost(navController= navController, startDestination = AppScreens.SplashScreen.route){
        composable(route = AppScreens.HomeScreen.route){
            navIsVisible = true
            HomeScreen(
                playersViewModel,
                coachesViewModel,
                calendarEventViewModel,
                matchesViewModel,
                teamsViewModel,
                navController
            )
        }
        composable(route = AppScreens.SecondScreen.route + "/{text}",
            listOf(navArgument(name = "text"){
                type = NavType.StringType
            })
        ){
            navIsVisible = true
            SecondScreen(navController, it.arguments?.getString("text"))
        }
        composable(route = AppScreens.LogScreen.route){
            navIsVisible = false
            LogScreen(navController, loginViewModel)
        }
        composable(route = AppScreens.SplashScreen.route){
            navIsVisible = false
            SplashScreen(navController)
        }
        composable(route = AppScreens.RegisterScreen.route) {
            navIsVisible = false
            RegisterScreen(playersViewModel, coachesViewModel)
        }
        composable(route = AppScreens.ProfileScreen.route + "/{id}/{isPlayer}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("isPlayer") { type = NavType.BoolType}
            )
        ) {
            navIsVisible = true
            val id = it.arguments?.getInt("id") ?: sessionManager.getUserId()
            val isPlayer = it.arguments?.getBoolean("isPlayer") ?: sessionManager.getRole()
            Log.d("PROFILE", "$id $isPlayer")
            ProfileScreen(navController, playersViewModel, coachesViewModel, teamsViewModel,
                playerStatsViewModel, id, isPlayer)
        }
        composable(route = AppScreens.CalendarScreen.route) {
            navIsVisible = true
            CalendarScreen(calendarEventViewModel, teamsViewModel)
        }
        composable(route = AppScreens.TeamScreen.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            navIsVisible = true
            val teamId = it.arguments?.getInt("id") ?: sessionManager.getTeamId()
            TeamScreen(navController, teamsViewModel, teamStatsViewModel, teamId)
        }
        composable(route = AppScreens.TeamStatsScreen.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            navIsVisible = true
            val teamId = it.arguments?.getInt("id") ?: sessionManager.getTeamId()
            TeamStatsScreen(navController, teamStatsViewModel, teamsViewModel, teamId)
        }
        composable(route = AppScreens.ClassificationScreen.route) {
            navIsVisible = true
            ClassificationScreen()
        }
        composable(route = AppScreens.TacticsScreen.route) {
            navIsVisible = true
            TacticsScreen(navController)
        }
        composable(route=AppScreens.MatchesScreen.route) {
            navIsVisible = true
            MatchesScreen()
        }
        composable(route=AppScreens.CoachModeScreen.route) {
            navIsVisible = true
            CoachModeScreen(navController)
        }
        composable(route=AppScreens.NewMatchScreen.route) {
            navIsVisible = false
            NewMatchScreen(
                navController,
                teamsViewModel,
                matchesViewModel,
                teamStatsViewModel,
                playerStatsViewModel,
                matchStatsViewModel
            )
        }
        composable(route = AppScreens.AddPlayerStatsScreen.route + "/{id}/{vsName}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("vsName") {type = NavType.StringType}
            )
        ) {
            val id = it.arguments?.getInt("id")
            val rivalName = it.arguments?.getString("vsName")
            navIsVisible = true
            if (id != null && rivalName != null) {
                AddPlayerStatsScreen(playersViewModel,navController, id, rivalName)
            }
        }

        /* Pantalla para editar la información de un usuario */
        composable(route = AppScreens.EditUserInfoScreen.route) {
            navIsVisible = false
            EditUserScreen(playersViewModel, coachesViewModel, teamsViewModel)
        }

        /* Pantalla de estadísticas de un jugador */
        composable(route = AppScreens.PlayerStatsScreen.route) {
            navIsVisible = false
            PlayerStatsScreen(matchStatsViewModel, playerStatsViewModel, matchesViewModel, 1, navController)
        }

        /* Pantalla de recuèración de contraseña */
        composable(route = AppScreens.MyPasswordScreen.route) {
            navIsVisible = false
            MyPasswordScreen()
        }

        /* Pantalla de Mi liga */
        composable(route = AppScreens.MiLigueScreen.route) {
            navIsVisible = false
            MiLigueScreen()
        }

        /* Pantalla de nuevo resultado */
        composable(route = AppScreens.NewResultScreen.route) {
            navIsVisible = false
            NewResultScreen()
        }

        /* Pantalla de partidos */
        composable(route = AppScreens.MatchScreen.route) {
            navIsVisible = false
            MatchScreen()
        }

        /* Pantalla de informes de tiro */
        composable(route = AppScreens.ShotInformScreen.route) {
            navIsVisible = false
            ShotInformScreen()
        }

        /* Pantalla de estadísticas de partido */
        composable(route = AppScreens.MatchStatsScreen.route) {
            navIsVisible = false
            MatchStatsScreen()
        }

        /* PANTALLA DEL MODO PIZARRA */
        composable(route = AppScreens.BoardScreen.route) {
            navIsVisible = false
            BoardScreen()
        }
    }
    if (navIsVisible) {
        Column(
            verticalArrangement = Arrangement.Bottom
        ) {
            B4AllNavigationBar(navController = navController)
        }
    }
}