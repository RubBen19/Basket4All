package com.example.basket4all.presentation.screens

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.basket4all.R
import com.example.basket4all.common.elements.LoadScreen
import com.example.basket4all.common.elements.byteArrayToBitmap
import com.example.basket4all.data.local.entities.PlayerEntity
import com.example.basket4all.presentation.navigation.AppScreens
import com.example.basket4all.presentation.viewmodels.db.TeamStatsViewModel
import com.example.basket4all.presentation.viewmodels.db.TeamViewModel
import com.example.basket4all.presentation.viewmodels.screens.TeamScreenViewModel
import com.example.basket4all.presentation.viewmodels.screens.TeamScreenViewModelFactory

@Composable
fun TeamScreen(navController: NavHostController, teamViewModel: TeamViewModel,
               teamStatsViewModel: TeamStatsViewModel, teamId: Int
) {
    val teamScreenViewModel: TeamScreenViewModel = viewModel(
        factory = TeamScreenViewModelFactory(teamViewModel, teamStatsViewModel, teamId)
    )

    val screenUiState by teamScreenViewModel.uiState.collectAsState()

    if (screenUiState.loading) {
        LoadScreen()
    }
    else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Portrait(
                    R.drawable.tigers_cb_removebg_preview__1_,
                    screenUiState.team?.name ?: "",
                    screenUiState.team?.category?.name ?: "",
                    screenUiState.team?.league ?: ""
                )
                StatsCard(
                    navController,
                    screenUiState.wins.toString(),
                    screenUiState.defeats.toString(),
                    screenUiState.points.toString(),
                    teamId
                )
                TeamCard(navController, screenUiState.players)
            }
        }
    }
}

//Función que contiene la presentación del club
@Composable
private fun Portrait(logoID: Int, name: String, category: String, league: String) {
    //Imagen de club
    Image(
        painter = painterResource(id = logoID),
        contentDescription = "Team picture",
        modifier = Modifier.size(180.dp)
    )
    //Nombre y datos
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Nombre
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 26.sp
        )
        //Categoria
        Text(
            text = category,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 24.sp
        )
        //Liga
        Text(
            text = league,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 20.sp
        )
    }
}

//Card de estadística de equipo
@Composable
private fun StatsCard(
    navController: NavHostController, win: String, loses: String, ppp: String, teamId: Int?
) {
    Row(
        modifier = Modifier
            .size(width = 344.dp, height = 120.dp)
            .padding(top = 12.dp)
            .border(
                width = 2.dp,
                brush = Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.tertiary
                    )
                ),
                shape = RectangleShape
            )
            .clickable {
                navController.navigate(AppScreens.TeamStatsScreen.route + "/$teamId")
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Tamaño del texto utilizado
        val fontSize = 20.sp
        //Icono
        Icon(
            painter = painterResource(id = R.drawable.query_stats_24px__1_),
            contentDescription = "Icon",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .clip(CircleShape)
                .size(96.dp)
                .padding(4.dp)
                .alpha(0.35f)
        )
        //Columna con los nombres de las estadísticas que se van a mostrar
        Column {
            Text(
                text = "Victorias",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = fontSize,
            )
            Text(
                text = "Derrotas",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = fontSize,
            )
            Text(
                text = "Puntos PP",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = fontSize,
            )
        }
        //Valores de las estadísticas
        Column (
            modifier = Modifier.padding(end = 24.dp)
        ) {
            //Victorias
            Text(
                text = win,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = fontSize,
            )
            //Derrotas
            Text(
                text = loses,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = fontSize,
            )
            //Puntos por partido
            Text(
                text = ppp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = fontSize,
            )
        }
    }
}

//Card de plantilla
@Composable
private fun TeamCard(navController: NavHostController, squad: List<PlayerEntity>) {
    Column(
        modifier = Modifier
            .padding(top = 12.dp)
            .size(width = 344.dp, height = 320.dp)
            .border(
                width = 2.dp,
                brush = Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.tertiary
                    )
                ),
                shape = RectangleShape
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Título
        Text(
            text = "Equipo",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 24.sp,
            modifier = Modifier.padding(4.dp)
        )
        //Lista en columna con los jugadores
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 4.dp)
        ) {
            items(squad) {p ->
                PlayerButton(navController, player = p)
            }
        }
    }
}

//Representa el botón de un jugador desde la card de equipo en club
@SuppressLint("DefaultLocale")
@Composable
private fun PlayerButton(navController: NavHostController, player: PlayerEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp, start = 20.dp, end = 20.dp, top = 8.dp)
            .clickable {
                val profileInfo = "/${player.id}/${true}"
                navController.navigate(AppScreens.ProfileScreen.route + profileInfo)
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val bitmap = byteArrayToBitmap(player.user.picture)
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Profile picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.primary,
                        CircleShape
                    )
                    .clip(CircleShape)
            )
        }
        else
            Image(
                painter = painterResource(id = R.drawable.blank_profile_photo),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(64.dp)
                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.primary,
                        CircleShape
                    )
                    .clip(CircleShape)
            )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = player.user.name,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp
            )
            Text(
                text = if(player.positions.isEmpty()) "" else player.positions.first().name,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp
            )
        }
        val number = String.format("%02d", player.number)
        Text(
            text = number,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 60.sp,
            letterSpacing = 0.sp,
            modifier = Modifier
                .alpha(0.5f)
                .padding(end = 4.dp)
        )
    }
}