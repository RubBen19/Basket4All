package com.example.basket4all.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.basket4all.common.elements.LoadScreen
import com.example.basket4all.common.messengers.SessionManager
import com.example.basket4all.presentation.uistate.EditUserScreenUiState
import com.example.basket4all.presentation.viewmodels.db.CoachesViewModel
import com.example.basket4all.presentation.viewmodels.db.PlayersViewModel
import com.example.basket4all.presentation.viewmodels.db.TeamViewModel
import com.example.basket4all.presentation.viewmodels.screens.EditUserScreenViewModel
import com.example.basket4all.presentation.viewmodels.screens.EditUserScreenViewModelFactory

@Composable
fun EditUserScreen(playersVM: PlayersViewModel,coachesVM: CoachesViewModel, teamVM: TeamViewModel) {

    val screenViewModel: EditUserScreenViewModel = viewModel(
        factory = EditUserScreenViewModelFactory(playersVM,coachesVM, teamVM)
    )
    val screenUiState by screenViewModel.uiState.collectAsState()

    if (screenUiState.loading) {
        LoadScreen()
    }
    else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Título de la screen
                Title()
                // Formulario con los campos a editar
                LazyColumn(
                    modifier = Modifier.padding(top = 20.dp, start = 12.dp, end = 12.dp)
                ) {
                    // Formulario
                    item {
                        Formulary(screenUiState, screenViewModel)
                    }
                }
            }
        }
    }

}

@Composable
private fun Title() {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "EDITAR ",
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "PERFIL",
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun Formulary(screenUiState: EditUserScreenUiState, screenVM: EditUserScreenViewModel) {
    Column {
        FormElement(
            title = "Nombre",
            oldValue = screenUiState.name,
            type = "Name",
            viewModel = screenVM
        )
        FormElement(
            title = "1er. Apellido",
            oldValue = screenUiState.surname,
            type = "First Surname",
            viewModel = screenVM
        )
        FormElement(
            title = "2º Apellido",
            type = "Second Surname",
            oldValue = screenUiState.surname2,
            viewModel = screenVM
        )
        FormElement(
            title = "Email",
            oldValue = screenUiState.email,
            type = "Email",
            viewModel = screenVM
        )
        if (SessionManager.getInstance().getRole()==true) {
            FormElement(
                title = "Dorsal",
                oldValue = screenUiState.number,
                type = "Dorsal",
                viewModel = screenVM
            )
        }
        Button(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(16),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 32.dp)
                .defaultMinSize(minWidth = 230.dp, minHeight = 48.dp)
        ) {
            Text(
                text = "Cambiar contraseña",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }
        Button(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(16),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 32.dp)
                .defaultMinSize(minWidth = 230.dp, minHeight = 48.dp)
        ) {
            Text(
                text = "Guardar cambios",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FormElement(
    title: String,
    oldValue: String = "",
    type: String,
    viewModel: EditUserScreenViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.background,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.onBackground,
                    RoundedCornerShape(4)
                )
                .defaultMinSize(minHeight = 44.dp, minWidth = 120.dp)
                .wrapContentSize(Alignment.Center)
        )
        TextField(
            value = oldValue,
            onValueChange = { viewModel.update(type, it) },
            keyboardOptions = KeyboardOptions(
                keyboardType = if (type == "Dorsal") KeyboardType.Number else KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
            singleLine = true,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground, fontSize = 16.sp),
            colors = TextFieldDefaults
                .textFieldColors(containerColor = MaterialTheme.colorScheme.background),
            modifier = Modifier
                .defaultMinSize(minWidth = 224.dp, minHeight = 40.dp)
                .padding(top = 8.dp)
        )
    }
}