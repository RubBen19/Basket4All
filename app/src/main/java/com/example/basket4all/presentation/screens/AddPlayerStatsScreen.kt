package com.example.basket4all.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.basket4all.common.classes.PlayerStatsClass
import com.example.basket4all.common.elements.LoadScreen
import com.example.basket4all.common.messengers.NewMatchCourier
import com.example.basket4all.data.local.entities.MatchStats
import com.example.basket4all.presentation.popup.AssistPopUp
import com.example.basket4all.presentation.popup.BlockPopUp
import com.example.basket4all.presentation.popup.FaultsPopUp
import com.example.basket4all.presentation.popup.FreeShotsPopUp
import com.example.basket4all.presentation.popup.LossesPopUp
import com.example.basket4all.presentation.popup.MinutesPopUp
import com.example.basket4all.presentation.popup.ReboundsPopUp
import com.example.basket4all.presentation.popup.ShotsOf2PopUp
import com.example.basket4all.presentation.popup.ShotsOf3PopUp
import com.example.basket4all.presentation.popup.StealsPopUp
import com.example.basket4all.presentation.viewmodels.db.PlayersViewModel
import com.example.basket4all.presentation.viewmodels.screens.AddPlayerScreenViewModel
import com.example.basket4all.presentation.viewmodels.screens.AddPlayerScreenViewModelFactory

@Composable
fun AddPlayerStatsScreen(
    playersViewModel: PlayersViewModel,
    navController: NavHostController,
    playerId: Int,
    rivalTeamName: String
) {
    val viewModel: AddPlayerScreenViewModel = viewModel(
        factory = AddPlayerScreenViewModelFactory(playersViewModel, playerId)
    )

    val loading by viewModel.loading.observeAsState(true)
    val name by viewModel.name.observeAsState("")
    val surname by viewModel.surname.observeAsState("")
    val surname2 by viewModel.surname2.observeAsState("")

    if (loading) {
        LoadScreen()
    }
    else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val stats = viewModel.matchStats.observeAsState(PlayerStatsClass())
            IconButton(
                onClick = {
                    viewModel.insert(stats.value)
                    navController.popBackStack()
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 56.dp, end = 8.dp)
                    .size(60.dp)
                    .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Done",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(60.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp, bottom = 36.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // Título de la screen
                Title()
                Subtitle(rivalTeamName)
                // Nombre del jugador
                PlayerName(name, surname, surname2)
                // Controlador de los PopUps
                PopUpController(viewModel)
                // Opciones
                Buttons(viewModel)
            }
        }
    }
}

@Composable
private fun Title() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Añadir ",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 32.sp
        )
        Text(
            text = "estadísticas",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 32.sp
        )
    }
}

@Composable
private fun Subtitle(rivalTeamName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "VS ",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 30.sp
        )
        Text(
            text = rivalTeamName,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 24.sp
        )
    }
}

@Composable
private fun PlayerName(name: String, surname: String, surname2: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "$name ",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 24.sp
        )
        Text(
            text = "$surname ",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 24.sp
        )
        Text(
            text = surname2,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 24.sp
        )
    }
}

@Composable
private fun PopUpController(viewModel: AddPlayerScreenViewModel) {
    val showMinutes by viewModel.showMinutes.observeAsState(false)
    val showShots2 by viewModel.showShots2.observeAsState(false)
    val showShots3 by viewModel.showShots3.observeAsState(false)
    val showFShots by viewModel.showFShots.observeAsState(false)
    val showAssist by viewModel.showAssist.observeAsState(false)
    val showBlocks by viewModel.showBlocks.observeAsState(false)
    val showFaults by viewModel.showFaults.observeAsState(false)
    val showRebound by viewModel.showRebounds.observeAsState(false)
    val showLosses by viewModel.showLosses.observeAsState(false)
    val showSteals by viewModel.showSteals.observeAsState(false)

    if (showMinutes) MinutesPopUp(viewModel)
    else if (showShots2) ShotsOf2PopUp(viewModel)
    else if (showShots3) ShotsOf3PopUp(viewModel)
    else if (showFShots) FreeShotsPopUp(viewModel)
    else if (showAssist) AssistPopUp(viewModel)
    else if (showBlocks) BlockPopUp(viewModel)
    else if (showRebound) ReboundsPopUp(viewModel)
    else if (showFaults) FaultsPopUp(viewModel)
    else if (showSteals) StealsPopUp(viewModel)
    else if (showLosses) LossesPopUp(viewModel)
}

@Composable
private fun Buttons(viewModel: AddPlayerScreenViewModel) {
    val optionsList = listOf(
        "Minutos",
        "Tiros de 2",
        "Tiros de 3",
        "Tiros libres",
        "Asistencias",
        "Rebotes",
        "Tapones",
        "Faltas",
        "Robos",
        "Pérdidas"
    )
    LazyColumn(
        modifier = Modifier.padding(top = 24.dp)
    ) {
        items(optionsList) { item ->
            Button(
                onClick = { viewModel.show(item) },
                shape = RoundedCornerShape(8),
                modifier = Modifier
                    .defaultMinSize(minWidth = 220.dp, minHeight = 48.dp)
            ) {
                Text(
                    text = item,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}



