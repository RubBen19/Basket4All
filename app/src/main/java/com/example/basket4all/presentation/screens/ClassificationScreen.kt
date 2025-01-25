package com.example.basket4all.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.basket4all.R
import com.example.basket4all.data.local.entities.TeamStats
import com.example.basket4all.ui.theme.Basket4allTheme
import kotlin.math.roundToInt

@Composable
fun ClassificationScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column {
            // Title
            Text(
                text = "Clasificación",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(12.dp)
            )
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val titles = listOf("", "Equipo", "PJ", "PG", "PP", "%PG", "PPP")
                titles.forEach { t ->
                    Text(
                        text = t,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            Divider(color = MaterialTheme.colorScheme.tertiary)
            val teamList = listOf(
                TeamStats(teamId = 1, matchPlayed = 6, wins = 6, points = 290, fouls = 12, turnovers = 8, rebounds = 45),
                TeamStats(teamId = 2, matchPlayed = 6, wins = 5, points = 285, fouls = 15, turnovers = 10, rebounds = 40),
                TeamStats(teamId = 3, matchPlayed = 6, wins = 4, points = 280, fouls = 18, turnovers = 12, rebounds = 38),
                TeamStats(teamId = 4, matchPlayed = 6, wins = 3, points = 275, fouls = 20, turnovers = 14, rebounds = 36),
                TeamStats(teamId = 5, matchPlayed = 6, wins = 2, points = 270, fouls = 22, turnovers = 16, rebounds = 33),
                TeamStats(teamId = 6, matchPlayed = 6, wins = 1, points = 265, fouls = 25, turnovers = 18, rebounds = 30),
                TeamStats(teamId = 7, matchPlayed = 6, wins = 0, points = 160, fouls = 28, turnovers = 20, rebounds = 28)
            )
            val images = listOf(
                R.drawable.logo1,
                R.drawable.logo2,
                R.drawable.logo3,
                R.drawable.logo4,
                R.drawable.logo5,
                R.drawable.tigers_cb_removebg_preview__1_,
                R.drawable.logo_default
            )
            LazyColumn {
                items(teamList.size) { place ->
                    val team = teamList[place]
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, start = 8.dp, end = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        // Position
                        Text(
                            text = (place + 1).toString(),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Image(
                            painter = painterResource(id = images[place]),
                            contentDescription = "Logo",
                            modifier = Modifier.size(60.dp)
                        )
                        Text(
                            text = team.matchPlayed.toString(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = team.wins.toString(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = (team.matchPlayed - team.wins).toString(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = (team.wins.toFloat().div(team.matchPlayed) * 100).roundToInt().toString() + "%",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = (team.points.toFloat().div(team.matchPlayed)).toString().take(4),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview(){
    Basket4allTheme {
        ClassificationScreen()
    }
}