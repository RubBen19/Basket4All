package com.example.basket4all.presentation.popup

import  androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.basket4all.presentation.uistate.AddPlayerScreenUiState
import com.example.basket4all.presentation.viewmodels.screens.AddPlayerScreenViewModel

@Composable
fun FaultsPopUp(viewModel: AddPlayerScreenViewModel, screenUiState: AddPlayerScreenUiState) {
    // Inputs
    val inside = remember { mutableStateOf("") }
    val outside = remember { mutableStateOf("") }

    AlertDialog(
        modifier = Modifier.padding(top = 20.dp, bottom = 32.dp),
        onDismissRequest = { viewModel.show("Faltas") },
        confirmButton = {
            IconButton(
                onClick = {
                    val faults = screenUiState.matchStats.faults
                    faults.setInFaults(inside.value.toIntOrNull() ?: 0)
                    faults.setOutFaults(outside.value.toIntOrNull() ?: 0)

                    viewModel.show("Faltas")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Añadir",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(80.dp)
                )
            }
        },
        shape = RoundedCornerShape(2),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Fal",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 32.sp
                )
                Text(
                    text = "tas",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 32.sp
                )
            }
        },
        text = {
            SuccessAndFails(inside, outside)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SuccessAndFails(success: MutableState<String>, failed: MutableState<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Dentro",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                value = success.value,
                onValueChange = { success.value = it },
                textStyle = TextStyle(color = MaterialTheme.colorScheme.background),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done,
                ),
                modifier = Modifier
                    .padding(end = 4.dp)
                    .defaultMinSize(minWidth = 80.dp, minHeight = 50.dp)
                    .width(80.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Fuera",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                value = failed.value,
                onValueChange = { failed.value = it },
                textStyle = TextStyle(color = MaterialTheme.colorScheme.background),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done,
                ),
                modifier = Modifier
                    .padding(start = 4.dp)
                    .defaultMinSize(minWidth = 80.dp, minHeight = 50.dp)
                    .width(80.dp)
            )
        }
    }
}