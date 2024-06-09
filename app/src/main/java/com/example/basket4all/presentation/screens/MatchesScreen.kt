package com.example.basket4all.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.basket4all.R
import com.example.basket4all.common.classes.Score
import com.example.basket4all.common.elements.TeamLogoChildScreens
import com.example.basket4all.common.enums.Categories
import com.example.basket4all.data.local.entities.MatchEntity
import com.example.basket4all.data.local.entities.TeamEntity
import com.example.basket4all.ui.theme.Basket4allTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun MatchesScreen() {
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
            // Logo equipo
            TeamLogoChildScreens("Partidos", "Jugados")
            // Lista de partidos jugados
            MatchesPlayedList()
        }
    }
}

val team1 = TeamEntity(1,1,"", "VILLA DE LEGANES CB", Categories.SENIOR, "")
val team2 = TeamEntity(1,1,"", "BASKET HOLOS SALESIANOS", Categories.SENIOR, "")

val testMatches = listOf(
    MatchEntity(
        1,
        6,
        1,
        LocalDate.of(2023, 10, 8),
        Score(67, 47)
    ),
    MatchEntity(
        2,
        1,
        5,
        LocalDate.of(2023, 10, 15),
        Score(68, 65)
    ),
    MatchEntity(
        4,
        1,
        3,
        LocalDate.of(2023, 10, 29),
        Score(48, 65)
    )
)
@Composable
private fun MatchesPlayedList() {
    //Lista en columna con todos los partidos
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 8.dp, start = 12.dp, end = 12.dp),
    ) {
        itemsIndexed(testMatches) { _,m ->
            val format = DateTimeFormatter.ofPattern("dd / MM / yyyy")
            MatchButton(team1, team2, m.score, m.date.format(format))
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun MatchButton(team1: TeamEntity, team2: TeamEntity, score: Score, date: String) {
    //Botón
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onBackground)
    ) {
        //Row imágenes y marcador
        MatchButtonScoreVs(score, date)
        // Row de nombres
        MatchButtonNames(team1, team2)
    }
}

@Composable
private fun MatchButtonNames(team1: TeamEntity, team2: TeamEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = team1.name,
            color = MaterialTheme.colorScheme.background,
            fontSize = 10.sp,
            modifier = Modifier
                .alpha(0.4f)
                .padding(start = 4.dp)
        )
        Text(
            text = team2.name,
            color = MaterialTheme.colorScheme.background,
            fontSize = 10.sp,
            modifier = Modifier
                .alpha(0.4f)
                .padding(start = 4.dp)
        )
    }
}

@Composable
private fun MatchButtonScoreVs(score: Score, date: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_default),
            contentDescription = "Team 1 logo",
            modifier = Modifier.size(80.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = score.getLocalScore().toString(),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 40.sp,
                    modifier = Modifier
                        .alpha(0.35f)
                )
                Text(
                    text = "VS",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp)
                )
                Text(
                    text = score.getVisitorScore().toString(),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 40.sp,
                    modifier = Modifier
                        .alpha(0.35f)
                )
            }
            Text(
                text = date,
                color = MaterialTheme.colorScheme.background,
                fontSize = 12.sp
            )
        }
        Image(
            painter = painterResource(id = R.drawable.logo_default),
            contentDescription = "Team 2 logo",
            modifier = Modifier.size(80.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() {
    Basket4allTheme {
        MatchesScreen()
    }
}