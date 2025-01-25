package com.example.basket4all.presentation.screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.basket4all.R
import com.example.basket4all.common.elements.LoadScreen
import com.example.basket4all.common.elements.MatchButton
import com.example.basket4all.common.elements.getDay
import com.example.basket4all.common.elements.getMonth
import com.example.basket4all.common.enums.EventType
import com.example.basket4all.data.local.entities.CalendarEventEntity
import com.example.basket4all.presentation.navigation.AppScreens
import com.example.basket4all.presentation.uistate.HomeScreenUiState
import com.example.basket4all.presentation.viewmodels.db.CalendarEventViewModel
import com.example.basket4all.presentation.viewmodels.db.CoachesViewModel
import com.example.basket4all.presentation.viewmodels.db.MatchesViewModel
import com.example.basket4all.presentation.viewmodels.db.PlayersViewModel
import com.example.basket4all.presentation.viewmodels.db.TeamViewModel
import com.example.basket4all.presentation.viewmodels.screens.HomeScreenViewModel
import com.example.basket4all.presentation.viewmodels.screens.HomeScreenViewModelFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen (
    playerVM: PlayersViewModel, coachesVM: CoachesViewModel, eventsVM: CalendarEventViewModel,
    matchesVM: MatchesViewModel, teamVM: TeamViewModel, navController: NavHostController
) {
    // Con el uso del BackHandler puedo detectar cuando se produce un evento "Back" en la screen.
    BackHandler {
        // Al detectarlo se redirige al usuario a la pantalla de login
        navController.navigate(AppScreens.LogScreen.route) {
            popUpTo(AppScreens.LogScreen.route) { inclusive = true }
        }
    }

    val viewModel: HomeScreenViewModel = viewModel(
        factory = HomeScreenViewModelFactory(playerVM, coachesVM, eventsVM, matchesVM, teamVM)
    )

    // Carga de datos cada vez que la screen sea visible, ya que solo se puede acceder a ella mediante la acción BACK
    LaunchedEffect(Unit) {
        viewModel.load()
    }

    val screenUiState by viewModel.uiState.collectAsState()

    if (screenUiState.loading) LoadScreen()
    else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            //Logo del equipo para el fondo
            Image(
                painter = painterResource(id = screenUiState.teamLogo),
                contentDescription = "Team picture",
                modifier = Modifier
                    .size(400.dp)
                    .align(Alignment.Center)
                    .alpha(0.2f)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 12.dp, bottom = 40.dp, start = 12.dp, end = 12.dp),
                verticalArrangement = Arrangement.Top
            ) {
                // Titulo con el nombre del usuario
                Title(name = screenUiState.username)
                // Cards con los proximos partidos y eventos
                HorizontalCards(screenUiState.events, screenUiState.matches, navController)
                // Card para el último partido jugado
                LastMatchPlayed(screenUiState, navController)
            }
        }
    }
}

@Composable
private fun Title(name: String) {
    Text(
        text = "BIENVENIDO",
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = name.uppercase(),
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
    )
    // Fecha de hoy
    DateOfToday()
}

@Composable
private fun DateOfToday() {
    Row {
        val dateOfToday = LocalDate.now()
        Text(
            text = "HOY ES ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "${getDay(dateOfToday.dayOfWeek).uppercase()} ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "${dateOfToday.dayOfMonth} ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "DE ${getMonth(dateOfToday.month)}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun HorizontalCards(
    events: List<CalendarEventEntity>,
    matches: List<CalendarEventEntity>,
    navController: NavHostController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MatchesCard(
            "PROX. PARTIDOS",
            "¿Preparado?",
            matches
        ) { navController.navigate(AppScreens.CalendarScreen.route) }
        EventsCard(
            "PROX. EVENTOS",
            "¿Estás al día?",
            events
        ) { navController.navigate(AppScreens.CalendarScreen.route) }
    }
}

@Composable
private fun LastMatchPlayed(screenUiState: HomeScreenUiState, navController: NavHostController) {
    if (screenUiState.lastMatch != null) {
        val format = DateTimeFormatter.ofPattern("dd / MM / yyyy")

        Row(
            modifier = Modifier
                .padding(top = 40.dp)
        ) {
            Text(
                text = "ÚLTIMO ",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "PARTIDO",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        if (screenUiState.localTeam != null && screenUiState.visitorTeam != null) {
            MatchButton(
                team1 = screenUiState.localTeam,
                team2 = screenUiState.visitorTeam,
                score = screenUiState.lastMatch.score,
                date = screenUiState.lastMatch.date.format(format),
                onClick = { navController.navigate(AppScreens.MatchScreen.route) }
            )
        }
    }
}

@Composable
private fun MatchesCard(title: String, subtitle: String, list: List<CalendarEventEntity>, clickable: ()->Unit = {}) {
    Column(
        modifier = Modifier
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
            .defaultMinSize(minWidth = 180.dp, minHeight = 292.dp)
            .clickable { clickable.invoke() },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = CenterHorizontally
    ) {
        //Titulo
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
        //Subtitulo
        Text(
            text = subtitle,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(4.dp)
        )
        ProxMatchEvent(listMatch = list)
    }
}

@Composable
private fun ProxMatchEvent(listMatch: List<CalendarEventEntity>) {
    for (match in listMatch) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "VS",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = match.description,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Composable
private fun EventsCard(title: String, subtitle: String, list: List<CalendarEventEntity>, clickable: ()->Unit = {}) {
    Column(
        modifier = Modifier
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
            .defaultMinSize(minWidth = 180.dp, minHeight = 292.dp)
            .clickable { clickable.invoke() },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = CenterHorizontally
    ) {
        //Titulo
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
        //Subtitulo
        Text(
            text = subtitle,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(4.dp)
        )
        ProxEvent(eventsList = list)
    }
}

@Composable
private fun ProxEvent(eventsList: List<CalendarEventEntity>) {
    for (event in eventsList) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = event.date.toString(),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = if (event.type == EventType.NOTE && event.description.isNotEmpty()) {
                    event.description.take(15)
                }
                else event.type.nombre,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}
