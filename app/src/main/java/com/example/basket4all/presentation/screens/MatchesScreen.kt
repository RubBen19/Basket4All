package com.example.basket4all.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basket4all.R
import com.example.basket4all.common.classes.MatchScore
import com.example.basket4all.common.classes.Score
import com.example.basket4all.common.elements.MatchButton
import com.example.basket4all.common.elements.TeamLogoChildScreens
import com.example.basket4all.common.enums.Categories
import com.example.basket4all.data.local.entities.MatchEntity
import com.example.basket4all.data.local.entities.TeamEntity
import com.example.basket4all.presentation.navigation.AppScreens
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
            TeamLogoChildScreens(R.drawable.logo2,"Partidos", "Jugados")
            // Lista de partidos jugados
            MatchesPlayedList()
        }
    }
}

val team1 = TeamEntity(1,1, name = "VILLA DE LEGANES CB", category = Categories.SENIOR, league =  "")
val team2 = TeamEntity(1,1, name = "BASKET HOLOS SALESIANOS", category = Categories.SENIOR, league = "")

val testMatches = listOf(
    MatchEntity(
        1,
        6,
        1,
        LocalDate.of(2023, 10, 8),
        MatchScore(
            Score(11, 17),
            Score(10, 23),
            Score(20, 16),
            Score(10, 17)
        )
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
            MatchButton(team1, team2, m.score, m.date.format(format)) {  }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() {
    Basket4allTheme {
        MatchesScreen()
    }
}