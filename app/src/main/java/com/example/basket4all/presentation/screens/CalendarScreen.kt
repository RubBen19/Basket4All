package com.example.basket4all.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.basket4all.common.elements.LoadScreen
import com.example.basket4all.presentation.popup.CalendarEvent
import com.example.basket4all.presentation.popup.RemoveConfirm
import com.example.basket4all.presentation.popup.ShowDayEvents
import com.example.basket4all.presentation.popup.ShowEvent
import com.example.basket4all.presentation.viewmodels.db.CalendarEventViewModel
import com.example.basket4all.presentation.viewmodels.screens.CalendarScreenViewModel
import com.example.basket4all.presentation.viewmodels.screens.CalendarScreenViewModelFactory
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarScreen(calendarEventViewModel: CalendarEventViewModel) {
    val viewModel: CalendarScreenViewModel = viewModel(
        factory = CalendarScreenViewModelFactory(calendarEventViewModel)
    )
    val loading by viewModel.loading.observeAsState(false)

    if (loading) LoadScreen()
    else {
        val year by viewModel.year.observeAsState(LocalDate.now().year)
        val month by viewModel.month.observeAsState(LocalDate.now().month)
        val showNewEvent by viewModel.showNewEventPopUp.observeAsState(false)
        val showEvent by viewModel.showEventPopUp.observeAsState(false)
        val showConfirm by viewModel.showConfirm.observeAsState(false)
        val showDay by viewModel.showDayEvents.observeAsState(false)

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Header(year, month, viewModel)
            DaysGrid(viewModel, year, month)
            Events(viewModel)
        }
        if (showNewEvent) CalendarEvent(viewModel)
        if (showEvent) ShowEvent(viewModel)
        if (showConfirm) RemoveConfirm(viewModel)
        if (showDay) ShowDayEvents(viewModel)
    }

}

private fun getMonth(month: Month?): String {
    return month?.getDisplayName(TextStyle.FULL, Locale.getDefault()).orEmpty().uppercase()
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

private fun getDaysOfWeek(): List<String> {
    return DayOfWeek.entries.map {
        it.getDisplayName(TextStyle.SHORT, Locale.getDefault()).uppercase()
    }
}
@Composable
fun DaysGrid(viewModel: CalendarScreenViewModel, year: Int, month: Month) {
    val days = YearMonth.of(year, month).lengthOfMonth()
    val daysOfWeek = getDaysOfWeek()
    var dayNumber = 1
    val events = viewModel.eventsList.observeAsState(listOf())

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
                        val date = LocalDate.of(year, month, dayNumber)
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(16.dp)
                                .clip(CircleShape)
                                .clickable {
                                    if (events.value.any { it.date == date }) {
                                        viewModel.showDayEvents(date)
                                    } else viewModel.showAddPopUp(date)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            var color = MaterialTheme.colorScheme.onBackground
                            if (events.value.any { it.date == date }) {
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
fun Events(viewModel: CalendarScreenViewModel) {
    val events = viewModel.eventsList.observeAsState(listOf())
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
        items(events.value) { e ->
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