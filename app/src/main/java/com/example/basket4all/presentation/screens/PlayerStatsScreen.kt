package com.example.basket4all.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.basket4all.R
import com.example.basket4all.common.elements.LoadScreen
import com.example.basket4all.data.local.entities.MatchStats
import com.example.basket4all.presentation.navigation.AppScreens
import com.example.basket4all.presentation.viewmodels.db.MatchStatsViewModel
import com.example.basket4all.presentation.viewmodels.db.MatchesViewModel
import com.example.basket4all.presentation.viewmodels.db.PlayerStatsViewModel
import com.example.basket4all.presentation.viewmodels.screens.PlayerStatsScreenViewModel
import com.example.basket4all.presentation.viewmodels.screens.PlayerStatsScreenViewModelFactory

@Composable
fun PlayerStatsScreen(
    matchStatsVM: MatchStatsViewModel,
    playerStatsVM: PlayerStatsViewModel,
    matchesVM: MatchesViewModel,
    playerID: Int,
    navController: NavHostController
) {

    // ViewModel
    val viewModel: PlayerStatsScreenViewModel = viewModel (
        factory = PlayerStatsScreenViewModelFactory(matchStatsVM, playerStatsVM, matchesVM, playerID)
    )

    // Estado de la pantalla
    val screenUiState by viewModel.uiState.collectAsState()

    if (screenUiState.loading) {
        LoadScreen()
    }
    else {
        val generalStats = listOf(
            "Puntos" to screenUiState.ppp.toString(),
            "Minutos" to screenUiState.mpp.toString(),
            "Asistencias" to screenUiState.app.toString(),
            "Rebotes" to screenUiState.rpp.toString(),
            "Partidos jugados" to screenUiState.mPlayed.toString(),
            "Faltas" to screenUiState.fpp.toString(),
            "Pérdidas" to screenUiState.lpp.toString(),
            "Robos" to screenUiState.spp.toString(),
            "Tapones" to screenUiState.bpp.toString()
        )
        val categories = listOf("De 2", "Triples", "Libres", "Zona")
        val success = listOf(
            screenUiState.twoPIn.toString(),
            screenUiState.ThreePIn.toString(),
            screenUiState.ZpIn.toString(),
            screenUiState.FpIn.toString()
        )
        val failed = listOf(
            screenUiState.twoPOut.toString(),
            screenUiState.ThreePOut.toString(),
            screenUiState.ZpOut.toString(),
            screenUiState.FpOut.toString()
        )
        val total = listOf(
            screenUiState.twoPShoots.toString(),
            screenUiState.ThreePShoots.toString(),
            screenUiState.ZpShoots.toString(),
            screenUiState.FpShoots.toString()
        )
        val percent = listOf(
            String.format("%.1f", screenUiState.twoPPercent),
            String.format("%.1f", screenUiState.ThreePPercent),
            String.format("%.1f", screenUiState.ZpPercent),
            String.format("%.1f", screenUiState.FpPercent)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Estadísticas generales por partido
                item {
                    GeneralStatsTable(
                        title = "Por partido",
                        stats = generalStats
                    )
                }
                // Estadísticas de tiro generales
                item {
                    AdvancedStatsTable(
                        title = "Tiro",
                        categories = categories,
                        successValues = success,
                        failValues = failed,
                        totalList = total,
                        percentList = percent
                    )
                    Button(onClick = { navController.navigate(AppScreens.ShotInformScreen.route) }) {
                        Text(
                            text = "Informe de tiro completo",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
                // Estadisitcas de pases clave
                item {
                    GeneralStatsTable(
                        title = "Pases clave",
                        stats = listOf(
                            "Total" to screenUiState.totalPasses.toString(),
                            "Asistencias" to screenUiState.assist.toString(),
                            "Probabilidad de asistencia" to screenUiState.probAssist.toString()
                        )
                    )
                }
                // Estadisitcas de rebotes
                item {
                    GeneralStatsTable(
                        title = "Rebotes",
                        stats = listOf(
                            "Ofensivo" to screenUiState.offensiveReb.toString(),
                            "Defensivo" to screenUiState.defensiveReb.toString()
                        )
                    )
                }
                // Estadisitcas de rebotes
                item {
                    GeneralStatsTable(
                        title = "Total",
                        stats = listOf(
                            "Faltas" to screenUiState.fouls.toString(),
                            "Pérdidas" to screenUiState.losts.toString(),
                            "Robos" to screenUiState.steals.toString(),
                            "Tapones" to screenUiState.blocks.toString()
                        )
                    )
                }
                // Estadisticas por partido
                item {
                    MatchesList(
                        viewModel = viewModel,
                        title = "Partidos jugados",
                        matches = screenUiState.matchesPlayed,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
private fun GeneralStatsTable(title: String, stats: List<Pair<String, String>>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Divider(
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        stats.forEachIndexed { index, (key, value) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = key,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (index % 2 == 0) {
                        MaterialTheme.colorScheme.primary
                    } else MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = value,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (index % 2 == 0) {
                        MaterialTheme.colorScheme.primary
                    } else MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
private fun AdvancedStatsTable(
    title: String, categories: List<String>, successValues: List<String>, failValues: List<String>,
    totalList: List<String>, percentList: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Divider(
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Columna con los nombres de las filas
            Column(horizontalAlignment = Alignment.Start) {
                Text(text = "", modifier = Modifier.padding(top = 3.dp, bottom = 2.dp))
                categories.forEachIndexed { index, name ->
                    Text(
                        text = name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (index % 2 == 0) {
                            MaterialTheme.colorScheme.primary
                        } else MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            // Columna con los valores acertados
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "OK",
                    tint = MaterialTheme.colorScheme.primary
                )
                successValues.forEach { value ->
                    Text(
                        text = value,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Green
                    )
                }
            }
            // Columna con los valores fallados
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "FAIL",
                    tint = MaterialTheme.colorScheme.onBackground
                )
                failValues.forEach { value ->
                    Text(
                        text = value,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                }
            }
            // Columna con los valores totales
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Total",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 3.dp, bottom = 2.dp)
                )
                totalList.forEach { value ->
                    Text(
                        text = value,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            // Columnas con los valores en porcentaje
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "", modifier = Modifier.padding(top = 3.dp, bottom = 2.dp))
                percentList.forEach { value ->
                    Text(
                        text = "$value%",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
private fun MatchesList(viewModel: PlayerStatsScreenViewModel, title: String, matches: List<MatchStats>, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Divider(
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        matches.forEach { match ->
            val matchInfo by viewModel.match.observeAsState()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { navController.navigate(AppScreens.MatchStatsScreen.route) }
                    .border(1.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(6)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_default),
                        contentDescription = "Logo default",
                        modifier = Modifier.size(100.dp)
                    )
                    Text(
                        text = matchInfo?.date.toString(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                Column {
                    Text(
                        text = "Puntos",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Minutos",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Asistencias",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Column {
                    Text(
                        text = match.stats.shots.getPoints().toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = match.stats.minutes.toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = match.stats.lastPass.getAssist().toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}