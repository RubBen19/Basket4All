package com.example.basket4all.presentation.screens

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.basket4all.common.elements.LoadScreen
import com.example.basket4all.common.elements.getDaysOfWeek
import com.example.basket4all.common.elements.getMonth
import com.example.basket4all.presentation.popup.CalendarEvent
import com.example.basket4all.presentation.popup.RemoveConfirm
import com.example.basket4all.presentation.popup.ShowDayEvents
import com.example.basket4all.presentation.popup.ShowEvent
import com.example.basket4all.presentation.uistate.CalendarScreenUiState
import com.example.basket4all.presentation.viewmodels.db.CalendarEventViewModel
import com.example.basket4all.presentation.viewmodels.db.TeamViewModel
import com.example.basket4all.presentation.viewmodels.screens.CalendarScreenViewModel
import com.example.basket4all.presentation.viewmodels.screens.CalendarScreenViewModelFactory
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth

@Composable
fun CalendarScreen(calendarEventViewModel: CalendarEventViewModel, teamViewModel: TeamViewModel) {
    val viewModel: CalendarScreenViewModel = viewModel(
        factory = CalendarScreenViewModelFactory(calendarEventViewModel, teamViewModel)
    )
    val screenUiState by viewModel.uiState.collectAsState()

    if (screenUiState.loading) LoadScreen()
    else {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Header(screenUiState.year, screenUiState.month, viewModel)
            DaysGrid(viewModel, screenUiState)
            Events(viewModel, screenUiState)
        }
        if (screenUiState.showNewEventPopUp) CalendarEvent(viewModel, screenUiState)
        if (screenUiState.showEventPopUp) ShowEvent(viewModel, screenUiState)
        if (screenUiState.showConfirm) RemoveConfirm(viewModel, screenUiState)
        if (screenUiState.showDayEvents) ShowDayEvents(viewModel, screenUiState)
    }
}

@Composable
fun Header(year: Int?, month: Month?, viewModel: CalendarScreenViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { viewModel.backMonth() }) {
            Icon(
                Icons.Default.ArrowBack,
                "Mes anterior",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )
        }
        Row {
            Text(
                text = getMonth(month),
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = " $year",
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
        IconButton(onClick = { viewModel.nextMonth() }) {
            Icon(
                Icons.Default.ArrowForward,
                "Mes anterior",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Composable
fun DaysGrid(viewModel: CalendarScreenViewModel, screenUiState: CalendarScreenUiState) {
    val days = YearMonth.of(screenUiState.year, screenUiState.month).lengthOfMonth()
    val daysOfWeek = getDaysOfWeek()
    var dayNumber = 1
    val events = screenUiState.eventsList

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            daysOfWeek.forEach { dayName ->
                Text(
                    text = dayName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        while (dayNumber <= days) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                var i = 0
                while (i < daysOfWeek.size){
                    if (dayNumber <= days) {
                        val date = LocalDate.of(screenUiState.year, screenUiState.month, dayNumber)
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(16.dp)
                                .clip(CircleShape)
                                .clickable {
                                    if (events.any { it.date == date }) {
                                        viewModel.showDayEvents(date)
                                    } else viewModel.showAddPopUp(date)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            var color = MaterialTheme.colorScheme.onBackground
                            if (events.any { it.date == date }) {
                                color = MaterialTheme.colorScheme.primary
                            }
                            Text(
                                text = dayNumber.toString(),
                                fontSize = 17.sp,
                                color = color
                            )
                        }
                    }
                    else {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(16.dp)
                        )
                    }
                    i++
                    dayNumber++
                }
            }
        }
    }
}

@Composable
fun Events(viewModel: CalendarScreenViewModel, screenUiState: CalendarScreenUiState) {
    Text(
        text = "PROXIMOS EVENTOS",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(8.dp)
    )
    LazyColumn(
        modifier = Modifier.padding(bottom = 52.dp)
    ) {
        items(screenUiState.eventsList) { e ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${e.type.nombre.uppercase()} ",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "${e.date.dayOfMonth}-${e.date.monthValue}-${e.date.year}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "${if (e.hour == null) "" else e.hour.toString()} ",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Row {
                    IconButton(onClick = { viewModel.showEvent(e) }) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Ver evento"
                        )
                    }
                    IconButton(onClick = { viewModel.showConfirmWindow(e) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar evento"
                        )
                    }
                }
            }
        }
    }
}