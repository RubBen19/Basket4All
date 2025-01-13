package com.example.basket4all.presentation.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.basket4all.R
import com.example.basket4all.common.elements.LoadScreen
import com.example.basket4all.presentation.navigation.AppScreens
import com.example.basket4all.presentation.viewmodels.db.MatchStatsViewModel
import com.example.basket4all.presentation.viewmodels.db.MatchesViewModel
import com.example.basket4all.presentation.viewmodels.db.PlayerStatsViewModel
import com.example.basket4all.presentation.viewmodels.db.TeamStatsViewModel
import com.example.basket4all.presentation.viewmodels.db.TeamViewModel
import com.example.basket4all.presentation.viewmodels.screens.NewMatchScreenViewModel
import com.example.basket4all.presentation.viewmodels.screens.NewMatchScreenViewModelFactory
import java.util.Calendar

@Composable
fun NewMatchScreen(
    navController: NavHostController,
    teamViewModel: TeamViewModel,
    matchesViewModel: MatchesViewModel,
    teamStatsViewModel: TeamStatsViewModel,
    playerStatsViewModel: PlayerStatsViewModel,
    matchStatsViewModel: MatchStatsViewModel
) {
    val viewModel: NewMatchScreenViewModel = viewModel(
        factory = NewMatchScreenViewModelFactory(
            teamViewModel, matchesViewModel, teamStatsViewModel, playerStatsViewModel, matchStatsViewModel
        )
    )
    val loading by viewModel.loading.observeAsState(false)
    val show by viewModel.playerSelectionShow.observeAsState(false)
    val playerSelected by viewModel.playersSelected.observeAsState(listOf())

    if (loading) {
        LoadScreen()
    }
    else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            //Botón para añadir el partido
            IconButton(
                onClick = {
                    viewModel.saveChanges()
                    navController.popBackStack()
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 56.dp, end = 8.dp)
                    .size(60.dp)
                    .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Done",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(60.dp)
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título de la screen
                Title()
                //Seleccionar jugadores (Pop-Up)
                if (show) PlayerSelectionPopUp(navController, viewModel)
                //Seleccionar equipo rival
                RivalSelection(viewModel)
                // Selector de fecha para el partido
                DateField(viewModel)
                //Marcador
                ScoreInput(viewModel)
                //Seleccionar si se ha sido local o visitante
                LocarOrVisitor(viewModel)
                //Botón para seleccionar los jugadores
                Button(
                    onClick = { viewModel.changePlayerSelectionShow() },
                    shape = RoundedCornerShape(8),
                    modifier = Modifier
                        .defaultMinSize(minWidth = 220.dp, minHeight = 70.dp)
                        .padding(top = 28.dp)
                ) {
                    Text(
                        text = "Añadir jugadores",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
                //Lista de jugadores convocados
                Text(
                    text = "Jugadores convocados",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 28.dp)
                )
                LazyColumn {
                    items(playerSelected) { playerName ->
                        Text(
                            text = playerName,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}

// Funcion que invoca un selector de fecha
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateField(viewModel: NewMatchScreenViewModel) {
    var selectedDate by remember { mutableStateOf("") }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            viewModel.changeDate(selectedDay, selectedMonth + 1, selectedYear)
        },
        year,
        month,
        day
    )

    OutlinedTextField(
        value = selectedDate,
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 60.dp, vertical = 4.dp)
            .clickable { datePickerDialog.show() },
        label = { Text("Fecha") },
        readOnly = true,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Introduce una fecha",
                modifier = Modifier.clickable { datePickerDialog.show() }
            )
        }
    )
}

@Composable
private fun LocarOrVisitor(viewModel: NewMatchScreenViewModel) {
    val selectedOption by viewModel.localOrVisitor.observeAsState()
    Text(
        text = "¿Has jugado como?",
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        fontSize = 20.sp,
        modifier = Modifier.padding(top = 20.dp)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        RadioButton(
            selected = selectedOption == "LOCAL",
            onClick = { viewModel.changeLocalOrVisitor() }
        )
        Text(
            text = "LOCAL ",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        RadioButton(
            selected = selectedOption == "VISITANTE",
            onClick = { viewModel.changeLocalOrVisitor() }
        )
        Text(
            text = "VISITANTE",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScoreInput(viewModel: NewMatchScreenViewModel) {
    val localScore by viewModel.localScore.observeAsState()
    val visitorScore by viewModel.visitorScore.observeAsState()

    Text(
        text = "Marcador",
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 20.sp,
        modifier = Modifier.padding(top = 20.dp)
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Local",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
            )
            OutlinedTextField(
                value = localScore.toString(),
                onValueChange = { viewModel.changeLocalScore(it) },
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done,
                ),
                modifier = Modifier.defaultMinSize(minWidth = 80.dp, minHeight = 50.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Visitante",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
            )
            OutlinedTextField(
                value = visitorScore.toString(),
                onValueChange = { viewModel.changeVisitorScore(it) },
                textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done,
                ),
                modifier = Modifier.defaultMinSize(minWidth = 80.dp, minHeight = 50.dp)
            )
        }
    }
}

@Composable
private fun RivalSelection(viewModel: NewMatchScreenViewModel) {
    val options = viewModel.vsTeams
    val optionSelected by viewModel.rival.observeAsState("")
    val expanded by viewModel.dropdownExpanded.observeAsState(false)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { viewModel.changeDropdownExpanded() },
            shape = RoundedCornerShape(8),
            modifier = Modifier
                .defaultMinSize(minWidth = 220.dp, minHeight = 70.dp)
                .padding(top = 28.dp)
        ) {
            Text(
                text = optionSelected,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 20.sp
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { viewModel.changeDropdownExpanded() }
        ) {
            options.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = item.name,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    onClick = {
                        viewModel.changeRival(item.name)
                        viewModel.changeDropdownExpanded()
                    }
                )
            }
        }
    }
}

@Composable
private fun PlayerSelectionPopUp(
    navController: NavHostController,
    viewModel: NewMatchScreenViewModel
) {
    val players by viewModel.players.observeAsState(listOf())
    val rival by viewModel.rival.observeAsState("")

    AlertDialog(
        onDismissRequest = {
            viewModel.updateSelected()
            viewModel.changePlayerSelectionShow()
        },
        confirmButton = {
            /*Navegar a la pantalla de estadísticas de jugador*/
            IconButton(
                onClick = {
                    viewModel.updateSelected()
                    viewModel.changePlayerSelectionShow()
                }) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Aceptar",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        title = {
            Text(
                text = "Selecciona los jugadores convocados",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.background,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Al seleccionar un jugador podrás añadir sus estadísticas",
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                LazyColumn {
                    itemsIndexed(players) { _, player ->
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp)
                                .clickable {
                                    navController.navigate(
                                        AppScreens.AddPlayerStatsScreen.route
                                                + "/${player.id}/${rival}"
                                    )
                                }
                        ) {
                            val imageId = R.drawable.blank_profile_photo
                            Image(
                                painter = painterResource(id = imageId),
                                contentDescription = "Profile picture",
                                modifier = Modifier
                                    .size(40.dp)
                                    .border(
                                        2.dp,
                                        MaterialTheme.colorScheme.primary,
                                        CircleShape
                                    )
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "${player.user.name} ${player.user.surname1} ${player.user.surname2}",
                                color = MaterialTheme.colorScheme.background,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

    )

}

@Composable
private fun Title() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Nuevo ",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 36.sp
        )
        Text(
            text = "partido",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 36.sp
        )
    }
}