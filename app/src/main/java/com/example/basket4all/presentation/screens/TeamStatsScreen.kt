package com.example.basket4all.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.basket4all.R
import com.example.basket4all.common.elements.LoadScreen
import com.example.basket4all.common.elements.TeamLogoChildScreens
import com.example.basket4all.data.local.entities.TeamStats
import com.example.basket4all.presentation.navigation.AppScreens
import com.example.basket4all.presentation.viewmodels.db.TeamStatsViewModel
import com.example.basket4all.presentation.viewmodels.db.TeamViewModel
import com.example.basket4all.presentation.viewmodels.screens.TeamStatsScreenViewModel
import com.example.basket4all.presentation.viewmodels.screens.TeamStatsScreenViewModelFactory

@Composable
fun TeamStatsScreen(
    navController: NavHostController,
    teamStatsViewModel: TeamStatsViewModel,
    teamsViewModel: TeamViewModel,
    teamId: Int
) {
    val teamStatsScreenViewModel: TeamStatsScreenViewModel = viewModel(
        factory = TeamStatsScreenViewModelFactory(teamStatsViewModel, teamsViewModel, teamId)
    )
    val loading by teamStatsScreenViewModel.loading.observeAsState(false)

    if (loading) {
        LoadScreen()
    }
    else {
        val stats by teamStatsScreenViewModel.stats.observeAsState()
        val image by teamStatsScreenViewModel.image.observeAsState(R.drawable.logo_default)
        Log.d("TSS", stats?.teamId.toString())
        //Fondo
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 12.dp, start = 8.dp, end = 8.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Escudo equipo
                TeamLogoChildScreens(image,"Estadísticas", "Globales")

                //Card de estadisticas
                StatsCard(stats)

                //Boton inferior de partido
                BottomsButtons(navController, "Partidos")
            }
        }
    }
}

@Composable
private fun StatsCard(stats: TeamStats?) {
    Row {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(340.dp)
                .padding(end = 1.dp)
                .background(MaterialTheme.colorScheme.onBackground),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            val fontSize = 18.sp
            val color = MaterialTheme.colorScheme.background
            val endPadding = 4.dp
            Text(
                text = "Partidos jugados",
                fontWeight = FontWeight.Bold,
                color = color,
                fontSize = fontSize,
                modifier = Modifier.padding(end = endPadding, top = 24.dp)
            )
            Text(
                text = "Victorias",
                fontWeight = FontWeight.Bold,
                color = color,
                fontSize = fontSize,
                modifier = Modifier.padding(end = endPadding)
            )
            Text(
                text = "Derrotas",
                fontWeight = FontWeight.Bold,
                color = color,
                fontSize = fontSize,
                modifier = Modifier.padding(end = endPadding)
            )
            Text(
                text = "Puntos por partido",
                fontWeight = FontWeight.Bold,
                color = color,
                fontSize = fontSize,
                modifier = Modifier.padding(end = endPadding)
            )
            Text(
                text = "Faltas por partido",
                fontWeight = FontWeight.Bold,
                color = color,
                fontSize = fontSize,
                modifier = Modifier.padding(end = endPadding)
            )
            Text(
                text = "Pérdidas por partido",
                fontWeight = FontWeight.Bold,
                color = color,
                fontSize = fontSize,
                modifier = Modifier.padding(end = endPadding)
            )
            Text(
                text = "Rebotes por partido",
                fontWeight = FontWeight.Bold,
                color = color,
                fontSize = fontSize,
                modifier = Modifier.padding(end = endPadding, bottom = 20.dp)
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 1.dp)
                .fillMaxWidth()
                .height(340.dp)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.tertiary
                        )
                    ),
                    shape = RectangleShape
                ),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            val fontSize = 36.sp
            val color = MaterialTheme.colorScheme.background
            val startPadding = 8.dp

            val matchPlayed = if (stats?.matchPlayed == 0) 1 else stats?.matchPlayed ?: 1
            Text(
                text = stats?.matchPlayed.toString(),
                fontWeight = FontWeight.Bold,
                color = color,
                fontSize = fontSize,
                modifier = Modifier.padding(start = startPadding)
            )
            Text(
                text = stats?.wins.toString(),
                fontWeight = FontWeight.Bold,
                color = color,
                fontSize = fontSize,
                modifier = Modifier.padding(start = startPadding)
            )
            val lost = (stats?.matchPlayed!! - (stats.wins))
            Text(
                text = lost.toString(),
                fontWeight = FontWeight.Bold,
                color = color,
                fontSize = fontSize,
                modifier = Modifier.padding(start = startPadding)
            )
            val ppp = (stats.points.toDouble() / matchPlayed)
            Text(
                text = ppp.toString(),
                fontWeight = FontWeight.Bold,
                color = color,
                fontSize = fontSize,
                modifier = Modifier.padding(start = startPadding)
            )
            val fpp = (stats.fouls.toDouble() / matchPlayed)
            Text(
                text = fpp.toString(),
                fontWeight = FontWeight.Bold,
                color = color,
                fontSize = fontSize,
                modifier = Modifier.padding(start = startPadding)
            )
            val tpp = stats.turnovers.toDouble() / matchPlayed
            Text(
                text = tpp.toString(),
                fontWeight = FontWeight.Bold,
                color = color,
                fontSize = fontSize,
                modifier = Modifier.padding(start = startPadding)
            )
            val rpp = stats.rebounds.toDouble() / matchPlayed
            Text(
                text = rpp.toString(),
                fontWeight = FontWeight.Bold,
                color = color,
                fontSize = fontSize,
                modifier = Modifier.padding(start = startPadding)
            )
        }
    }
}

@Composable
private fun BottomsButtons(navController: NavHostController, text1: String) {
    //Botón para ver los partidos
    Column(
        modifier = Modifier
            .padding(top = 12.dp)
            .fillMaxWidth(0.5f)
            .fillMaxHeight(0.25f)
            .border(
                width = 2.dp,
                brush = Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.onBackground,
                        MaterialTheme.colorScheme.primary
                    )
                ),
                shape = RectangleShape
            )
            .clickable { navController.navigate(AppScreens.MatchesScreen.route) },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text1,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 20.sp
        )
    }
}