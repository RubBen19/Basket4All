package com.example.basket4all.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.basket4all.R
import com.example.basket4all.common.classes.PlayerStatsClass
import com.example.basket4all.ui.theme.Basket4allTheme

@Composable
fun ShotInformScreen() {
    val success = listOf("8", "1", "2", "12", "6")
    val failed = listOf("6", "4", "0", "8", "2")
    val total = listOf("14", "5", "2", "20", "8")
    val percent = listOf("57", "20", "100", "60", "66.6")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Estadísticas generales por partido
            item {
                AdvancedStatsTable(
                    title = "Media distancia",
                    categories = listOf("Esquina izquierda", "45 izquierda", "Centro", "45 derecha", "Esquina derecha"),
                    successValues = success,
                    failValues = failed,
                    totalList = total,
                    percentList = percent
                )
            }
            // Estadísticas de tiro generales
            item {
                AdvancedStatsTable(
                    title = "Triple",
                    categories = listOf("Esquina izquierda", "45 izquierda", "Centro", "45 derecha", "Esquina derecha"),
                    successValues = success,
                    failValues = failed,
                    totalList = total,
                    percentList = percent
                )
            }
            // Estadisitcas de pases clave
            item {
                AdvancedStatsTable(
                    title = "Desde zona",
                    categories = listOf("Izquierda", "Centro", "Derecha"),
                    successValues = listOf("8", "9", "6"),
                    failValues = listOf("2", "1", "4"),
                    totalList = listOf("10", "10", "10"),
                    percentList = listOf("80", "90", "60")
                )
            }
            // Estadisitcas de rebotes
            item {
                AdvancedStatsTable(
                    title = "Tiros libres",
                    categories = listOf(""),
                    successValues = listOf("8"),
                    failValues = listOf("2"),
                    totalList = listOf("10"),
                    percentList = listOf("80")
                )
            }
        }
    }
}

@Composable
private fun AdvancedStatsTable(
    title: String, categories: List<String>, successValues: List<String>, failValues: List<String>,
    totalList: List<String>, percentList: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Divider(
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Columna con los nombres de las filas
            Column(horizontalAlignment = Alignment.Start) {
                Text(text = "", modifier = Modifier.padding(top = 3.dp, bottom = 2.dp))
                categories.forEachIndexed { index, name ->
                    Text(
                        text = name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (index % 2 == 0) {
                            MaterialTheme.colorScheme.primary
                        } else MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            // Columna con los valores acertados
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "OK",
                    tint = MaterialTheme.colorScheme.primary
                )
                successValues.forEach { value ->
                    Text(
                        text = value,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Green
                    )
                }
            }
            // Columna con los valores fallados
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "FAIL",
                    tint = MaterialTheme.colorScheme.onBackground
                )
                failValues.forEach { value ->
                    Text(
                        text = value,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                }
            }
            // Columna con los valores totales
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Total",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 3.dp, bottom = 2.dp)
                )
                totalList.forEach { value ->
                    Text(
                        text = value,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            // Columnas con los valores en porcentaje
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "", modifier = Modifier.padding(top = 3.dp, bottom = 2.dp))
                percentList.forEach { value ->
                    Text(
                        text = "$value%",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun Preview() {
    Basket4allTheme {
        ShotInformScreen()
    }
}