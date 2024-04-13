package com.example.basket4all.presentation.navigation

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
import com.example.basket4all.common.elements.B4AllNavigationBar
import com.example.basket4all.data.local.AppDatabase
import com.example.basket4all.presentation.screens.CalendarScreen
import com.example.basket4all.presentation.screens.ExerciseScreen
import com.example.basket4all.presentation.screens.FirstScreen
import com.example.basket4all.presentation.screens.LogScreen
import com.example.basket4all.presentation.screens.ProfileScreen
import com.example.basket4all.presentation.screens.RegisterScreen
import com.example.basket4all.presentation.screens.SecondScreen
import com.example.basket4all.presentation.screens.SplashScreen
import com.example.basket4all.presentation.screens.TacticsScreen
import com.example.basket4all.presentation.screens.TeamScreen
import com.example.basket4all.presentation.viewmodels.CoachesViewModel
import com.example.basket4all.presentation.viewmodels.CoachesViewModelFactory
import com.example.basket4all.presentation.viewmodels.PlayersViewModel
import com.example.basket4all.presentation.viewmodels.PlayersViewModelFactory

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

    //Room y DAO
    val appDatabase = AppDatabase.getDatabase(context = LocalContext.current.applicationContext)
    val playerDao = appDatabase.playerDao()
    val coachDao = appDatabase.coachDao()

    //ViewModels relacionados con la base de datos
    val playersViewModel: PlayersViewModel = viewModel(factory = PlayersViewModelFactory(playerDao))
    val coachesViewModel: CoachesViewModel = viewModel(factory = CoachesViewModelFactory(coachDao))

    //ViewModels relacionados con screens


    //Con NavHost almaceno y gestiono las pantallas
    NavHost(navController= navController, startDestination = AppScreens.SplashScreen.route){
        composable(route = AppScreens.FirstScreen.route){
            navIsVisible = true
            FirstScreen(navController)
        }
        composable(route = AppScreens.SecondScreen.route + "/{text}",
            listOf(navArgument(name = "text"){
                type = NavType.StringType
            })
        ){
            SecondScreen(navController, it.arguments?.getString("text"))
        }
        composable(route = AppScreens.LogScreen.route){
            navIsVisible = false
            LogScreen(navController, playersViewModel, coachesViewModel)
        }
        composable(route = AppScreens.SplashScreen.route){
            navIsVisible = false
            SplashScreen(navController)
        }
        composable(route = AppScreens.RegisterScreen.route) {
            navIsVisible = false
            RegisterScreen(navController)
        }
        composable(route = AppScreens.ProfileScreen.route) {
            navIsVisible = true
            ProfileScreen()
        }
        composable(route = AppScreens.CalendarScreen.route) {
            navIsVisible = true
            CalendarScreen(navController)
        }
        composable(route = AppScreens.TeamScreen.route) {
            navIsVisible = true
            TeamScreen(navController)
        }
        composable(route = AppScreens.ExerciseScreen.route) {
            navIsVisible = true
            ExerciseScreen(navController)
        }
        composable(route = AppScreens.TacticsScreen.route) {
            navIsVisible = true
            TacticsScreen(navController)
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