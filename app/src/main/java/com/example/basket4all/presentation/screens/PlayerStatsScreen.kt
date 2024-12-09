package com.example.basket4all.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.basket4all.R
import com.example.basket4all.common.classes.PlayerStatsClass
import com.example.basket4all.ui.theme.Basket4allTheme

@Composable
fun PlayerStatsScreen() {
    val generalStats = listOf(
        "Puntos" to "8.2",
        "Minutos" to "12.5",
        "Asistencias" to "2.5",
        "Rebotes" to "12.1",
        "Partidos jugados" to "5",
        "Faltas" to "0.6",
        "Pérdidas" to "0.4",
        "Robos" to "0.8",
        "Tapones" to "1.2"
    )
    val categories = listOf("De 2", "Triples", "Libres", "Zona")
    val success = listOf("8", "1", "2", "12")
    val failed = listOf("6", "4", "0", "8")
    val total = listOf("14", "5", "2", "20")
    val percent = listOf("57", "20", "100", "60")
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
            }
            // Estadisitcas de pases clave
            item {
                GeneralStatsTable(
                    title = "Pases clave",
                    stats = listOf(
                        "Total" to "18",
                        "Asistencias" to "9",
                        "Probabilidad de asistencia" to "50%"
                    )
                )
            }
            // Estadisitcas de rebotes
            item {
                GeneralStatsTable(
                    title = "Rebotes",
                    stats = listOf(
                        "Ofensivo" to "12",
                        "Defensivo" to "20"
                    )
                )
            }
            // Estadisitcas de rebotes
            item {
                GeneralStatsTable(
                    title = "Total",
                    stats = listOf(
                        "Faltas" to "12",
                        "Pérdidas" to "20",
                        "Robos" to "4",
                        "Tapones" to "6"
                    )
                )
            }
            // Estadisticas por partido
            item {
                MatchesList(
                    title = "Partidos jugados",
                    matches = listOf(
                        PlayerStatsClass()
                    )
                )
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
private fun MatchesList(title: String, matches: List <PlayerStatsClass>) {
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
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
                        text = "17/10/2024",
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
                        text = match.shots.getPoints().toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = match.minutes.toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = match.lastPass.getAssist().toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun Preview() {
    Basket4allTheme {
        PlayerStatsScreen()
    }
}