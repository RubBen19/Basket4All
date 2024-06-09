package com.example.basket4all.presentation.popup

import  androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
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
import com.example.basket4all.presentation.viewmodels.screens.AddPlayerScreenViewModel

@Composable
fun ShotsOf2PopUp(viewModel: AddPlayerScreenViewModel) {
    // Inputs desde la esquina
    val successCL = remember { mutableStateOf("") }
    val failedCL = remember { mutableStateOf("") }
    val successCR = remember { mutableStateOf("") }
    val failedCR = remember { mutableStateOf("") }
    // Inputs desde 45º
    val success45L = remember { mutableStateOf("") }
    val failed45L = remember { mutableStateOf("") }
    val success45R = remember { mutableStateOf("") }
    val failed45R = remember { mutableStateOf("") }
    // Inputs desde el centro
    val successCenter = remember { mutableStateOf("") }
    val failedCenter = remember { mutableStateOf("") }
    // Inputs desde la zona
    val successZR = remember { mutableStateOf("") }
    val failedZR = remember { mutableStateOf("") }
    val successZL = remember { mutableStateOf("") }
    val failedZL = remember { mutableStateOf("") }
    val successZC = remember { mutableStateOf("") }
    val failedZC = remember { mutableStateOf("") }

    AlertDialog(
        modifier = Modifier.padding(top = 20.dp, bottom = 32.dp),
        onDismissRequest = { viewModel.hide("Tiros de 2") },
        confirmButton = {
            IconButton(
                onClick = {
                    val shots45 = viewModel.matchStats.value?.shots?.get2Shots()
                    val cornerShots = viewModel.matchStats.value?.shots?.get2Shots()
                    val centerShots = viewModel.matchStats.value?.shots?.get2Shots()
                    val zoneShots = viewModel.matchStats.value?.shots?.getZoneShots()

                    cornerShots?.setCornerL(
                        successCL.value.toIntOrNull() ?: 0,
                        failedCL.value.toIntOrNull() ?: 0
                    )
                    cornerShots?.setCornerR(
                        successCR.value.toIntOrNull() ?: 0,
                        failedCR.value.toIntOrNull() ?: 0
                    )
                    shots45?.setForty5L(
                        success45L.value.toIntOrNull() ?: 0,
                        failed45L.value.toIntOrNull() ?: 0
                    )
                    shots45?.setForty5R(
                        success45R.value.toIntOrNull() ?: 0,
                        failed45R.value.toIntOrNull() ?: 0
                    )
                    centerShots?.setCenter(
                        successCenter.value.toIntOrNull() ?: 0,
                        failedCenter.value.toIntOrNull() ?: 0
                    )
                    zoneShots?.setLeft(
                        successZL.value.toIntOrNull() ?: 0,
                        failedZL.value.toIntOrNull() ?: 0
                    )
                    zoneShots?.setRight(
                        successZR.value.toIntOrNull() ?: 0,
                        failedZR.value.toIntOrNull() ?: 0
                    )
                    zoneShots?.setCenter(
                        successZC.value.toIntOrNull() ?: 0,
                        failedZC.value.toIntOrNull() ?: 0
                    )

                    viewModel.hide("Tiros de 2")
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
                    text = "Tiros ",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 32.sp
                )
                Text(
                    text = "de 2",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 32.sp
                )
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                item { Corner(successCL, failedCL, successCR, failedCR) }
                item { Forty5(success45L, failed45L, success45R, failed45R) }
                item { Center(successCenter, failedCenter) }
                item { Zone(successZL, failedZL, successZC, failedZC, successZR, failedZR) }
            }
        }
    )
}

@Composable
private fun Corner(
    successCL: MutableState<String>,
    failedCL: MutableState<String>,
    successCR: MutableState<String>,
    failedCR: MutableState<String>
) {

    Text(
        text = "Esquina",
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary,
        fontSize = 28.sp,
        modifier = Modifier.padding(bottom = 12.dp)
    )
    Text(
        text = "Izquierda",
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.background,
        fontSize = 20.sp,
        modifier = Modifier.padding(bottom = 4.dp)
    )
    SuccessAndFails(successCL, failedCL)


    Text(
        text = "Derecha",
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.background,
        fontSize = 20.sp,
        modifier = Modifier.padding(bottom = 4.dp)
    )
    SuccessAndFails(successCR, failedCR)
}

@Composable
private fun Forty5(
    success45L: MutableState<String>,
    failed45L: MutableState<String>,
    success45R: MutableState<String>,
    failed45R: MutableState<String>
) {

    Text(
        text = "45º",
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary,
        fontSize = 28.sp,
        modifier = Modifier.padding(bottom = 12.dp, top = 8.dp)
    )
    Text(
        text = "Izquierda",
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.background,
        fontSize = 20.sp,
        modifier = Modifier.padding(bottom = 4.dp)
    )
    SuccessAndFails(success45L, failed45L)


    Text(
        text = "Derecha",
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.background,
        fontSize = 20.sp,
        modifier = Modifier.padding(bottom = 4.dp)
    )
    SuccessAndFails(success45R, failed45R)
}

@Composable
private fun Center(
    success: MutableState<String>,
    failed: MutableState<String>
) {

    Text(
        text = "Centro",
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary,
        fontSize = 28.sp,
        modifier = Modifier.padding(bottom = 12.dp, top = 8.dp)
    )
    SuccessAndFails(success, failed)
}

@Composable
private fun Zone(
    successZL: MutableState<String>,
    failedZL: MutableState<String>,
    successZC: MutableState<String>,
    failedZC: MutableState<String>,
    successZR: MutableState<String>,
    failedZR: MutableState<String>
) {

    Text(
        text = "Zona",
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary,
        fontSize = 28.sp,
        modifier = Modifier.padding(bottom = 12.dp, top = 8.dp)
    )
    Text(
        text = "Izquierda",
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.background,
        fontSize = 20.sp,
        modifier = Modifier.padding(bottom = 4.dp)
    )
    SuccessAndFails(successZL, failedZL)

    Text(
        text = "Derecha",
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.background,
        fontSize = 20.sp,
        modifier = Modifier.padding(bottom = 4.dp)
    )
    SuccessAndFails(successZR, failedZR)

    Text(
        text = "Centro",
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.background,
        fontSize = 20.sp,
        modifier = Modifier.padding(bottom = 4.dp)
    )
    SuccessAndFails(successZC, failedZC)
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
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = "Aciertos",
                tint = MaterialTheme.colorScheme.primary
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
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Fallos",
                tint = MaterialTheme.colorScheme.primary
            )
            OutlinedTextField(
                value = failed.value,
                onValueChange = { failed.value = it },
                textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
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