package com.example.basket4all.presentation.popup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.basket4all.common.enums.EventType
import com.example.basket4all.common.messengers.SessionManager
import com.example.basket4all.data.local.entities.CalendarEventEntity
import com.example.basket4all.presentation.viewmodels.screens.CalendarScreenViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarEvent(viewModel: CalendarScreenViewModel) {
    val type = remember { mutableStateOf(EventType.NOTE) }
    var description by remember { mutableStateOf("") }
    var place by remember { mutableStateOf("") }
    var hour by remember { mutableStateOf("") }
    val hourValidate = remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = { viewModel.hideAddPopUp() },
        confirmButton = {
            IconButton(
                onClick = {
                    hourValidate.value = try {
                        LocalTime.parse(hour, DateTimeFormatter.ofPattern("HH:mm"))
                        val calendarEvent = CalendarEventEntity(
                            teamId = SessionManager.getInstance().getTeamId(),
                            date = viewModel.date,
                            type = type.value,
                            description = description,
                            place = place,
                            hour = LocalTime.parse(hour)
                        )
                        viewModel.newEvent(calendarEvent)
                        viewModel.hideAddPopUp()
                        true
                    }
                    catch (error: DateTimeParseException) { false }
                    if (type.value == EventType.NOTE) {
                        val calendarEvent = CalendarEventEntity(
                            teamId = SessionManager.getInstance().getTeamId(),
                            date = viewModel.date,
                            type = type.value,
                            description = description,
                            place = place,
                            hour = null
                        )
                        viewModel.newEvent(calendarEvent)
                        viewModel.hideAddPopUp()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Guardar",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(80.dp)
                )
            }

        },
        title = {
            Text(
                text = "NUEVO EVENTO",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = {
            Column {
                // Seleccionar el tipo de evento
                SelectEventType(type)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(text = "¿Algo que añadir?") },
                    textStyle = TextStyle(fontSize = 14.sp)
                )
                if (type.value != EventType.NOTE) {
                    OutlinedTextField(
                        value = place,
                        onValueChange = { place = it },
                        label = { Text(text = "¿A donde vamos?") },
                        maxLines = 1,
                        textStyle = TextStyle(fontSize = 14.sp)
                    )
                    OutlinedTextField(
                        value = hour,
                        onValueChange = { hour = it },
                        label = { Text(text = "¿Hora? (HH:mm)") },
                        isError = !hourValidate.value,
                        textStyle = TextStyle(fontSize = 14.sp)
                    )
                    if (!hourValidate.value) {
                        Text(
                            text = "La hora introducida no es correcta, recuerda HH:mm",
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun SelectEventType(type: MutableState<EventType>) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = type.value == EventType.TRAINING,
            onClick = { type.value = EventType.TRAINING }
        )
        Text(
            text = EventType.TRAINING.nombre.uppercase(),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.background,
            fontSize = 16.sp
        )
    }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = type.value == EventType.MATCH,
            onClick = { type.value = EventType.MATCH }
        )
        Text(
            text = EventType.MATCH.nombre.uppercase(),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.background,
            fontSize = 16.sp
        )
    }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = type.value == EventType.NOTE,
            onClick = { type.value = EventType.NOTE }
        )
        Text(
            text = EventType.NOTE.nombre.uppercase(),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.background,
            fontSize = 16.sp
        )
    }
}

@Composable
fun ShowEvent(viewModel: CalendarScreenViewModel) {
    val event by viewModel.event.observeAsState()
    if (event != null) {
        AlertDialog(
            onDismissRequest = { viewModel.hideEvent() },
            confirmButton = {
                IconButton(
                    onClick = {
                        viewModel.hideEvent()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Salir",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(80.dp)
                    )
                }
            },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${event!!.type.nombre}  ",
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = event!!.date.toString(),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.background
                    )
                }
            },
            text = {
                Column {
                    Text(
                        text = "Descripción",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = event?.description ?: "Sin descripción",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.background,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Hora",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = if (event!!.hour == null) "" else event!!.hour.toString(),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.background,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Lugar",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = if (event!!.place == null) "" else event!!.place.toString(),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }
        )
    }
}

@Composable
fun RemoveConfirm(viewModel: CalendarScreenViewModel) {
    val event by viewModel.event.observeAsState()
    AlertDialog(
        onDismissRequest = {
            viewModel.hideConfirmWindow()
        },
        confirmButton = {
            IconButton(onClick = {
                event?.let { viewModel.removeEvent(it) }
                viewModel.hideConfirmWindow()
            }) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Aceptar"
                )
            }
        },
        dismissButton = {
            IconButton(onClick = { viewModel.hideConfirmWindow() }) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Cancelar"
                )
            }
        },
        title = {
            Text(
                text = "IMPORTANTE",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = {
            Text(
                text = "Esta acción no es reversible, el borrado será permamente",
                color = MaterialTheme.colorScheme.background
            )
        }
    )
}

@Composable
fun ShowDayEvents(viewModel: CalendarScreenViewModel) {
    val events by viewModel.dailyList.observeAsState(listOf())
    viewModel.loadDailyEvents()
    val date = viewModel.date
    AlertDialog(
        onDismissRequest = { viewModel.hideDayEvents() },
        dismissButton = {
            IconButton(onClick = { viewModel.showAddPopUp(date) }) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Añadir",
                    modifier = Modifier.size(60.dp)
                )
            }
        },
        confirmButton = {
            IconButton(onClick = { viewModel.hideDayEvents() }) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Cancelar",
                    modifier = Modifier.size(60.dp)
                )
            }
        },
        title = {
            Row {
                Text(
                    text = "EVENTOS ",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp
                )
                Text(
                    text = " ${date.dayOfMonth}-${date.monthValue}-${date.year}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 20.sp
                )
            }

        },
        text = {
            LazyColumn(
                modifier = Modifier.padding(bottom = 52.dp)
            ) {
                items(events) { e ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${e.type.nombre.uppercase()} ",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.background
                        )
                        Text(
                            text = "${e.date.dayOfMonth}-${e.date.monthValue}-${e.date.year}",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.background
                        )
                        Text(
                            text = "${if (e.hour == null) "" else e.hour.toString()} ",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.background
                        )
                        Row {
                            IconButton(onClick = { viewModel.showEvent(e) }) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = "Ver evento",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            IconButton(onClick = { viewModel.showConfirmWindow(e) }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar evento",
                                    tint = MaterialTheme.colorScheme.background
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}