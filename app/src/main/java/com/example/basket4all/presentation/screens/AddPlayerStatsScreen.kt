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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.basket4all.common.elements.LoadScreen
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
import com.example.basket4all.presentation.uistate.AddPlayerScreenUiState
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

    val screenUiState by viewModel.uiState.collectAsState()

    if (screenUiState.loading) {
        LoadScreen()
    }
    else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            IconButton(
                onClick = {
                    viewModel.insert(screenUiState.matchStats)
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
                PlayerName(screenUiState.name, screenUiState.surname, screenUiState.surname2)
                // Controlador de los PopUps
                PopUpController(screenUiState, viewModel)
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
private fun PopUpController(
    screenUiState: AddPlayerScreenUiState,
    viewModel: AddPlayerScreenViewModel
) {
    if (screenUiState.showMinutes) MinutesPopUp(viewModel, screenUiState)
    else if (screenUiState.showShots2) ShotsOf2PopUp(viewModel, screenUiState)
    else if (screenUiState.showShots3) ShotsOf3PopUp(viewModel, screenUiState)
    else if (screenUiState.showFShots) FreeShotsPopUp(viewModel, screenUiState)
    else if (screenUiState.showAssist) AssistPopUp(viewModel, screenUiState)
    else if (screenUiState.showBlocks) BlockPopUp(viewModel, screenUiState)
    else if (screenUiState.showRebounds) ReboundsPopUp(viewModel, screenUiState)
    else if (screenUiState.showFaults) FaultsPopUp(viewModel, screenUiState)
    else if (screenUiState.showSteals) StealsPopUp(viewModel, screenUiState)
    else if (screenUiState.showLosses) LossesPopUp(viewModel, screenUiState)
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



