package com.example.basket4all.common.elements

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.basket4all.R
import com.example.basket4all.common.classes.Score
import com.example.basket4all.common.enums.Categories
import com.example.basket4all.data.local.entities.TeamEntity
import java.io.ByteArrayOutputStream
import java.net.URI
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

/**
 * ARCHIVO DESTINADO A LA CREACIÓN DE FUNCIONES QUE SE UTILIZAN O BIEN PODRÍAN LLEGAR A UTILIZARSE
 * EN CUALQUIER PARTE DEL CÓDIGO DE LA APP
 */

// Función que partiendo de una fecha de nacimiento devuelve la edad (No precisa)
fun yearsCalculator (birthdate: LocalDate): Int{
    val actualDate = LocalDateTime.now()
    return (actualDate.year - birthdate.year)
}

// Función que partiendo de una fecha devuelve la categoria que le corresponde
fun categoryAssigner(date: LocalDate): Categories {
    return when (yearsCalculator(date)) {
        6,7 -> Categories.PREBENJAMIN
        8,9 -> Categories.BENJAMIN
        10,11 -> Categories.ALEVIN
        12,13 -> Categories.INFANTIL
        14,15 -> Categories.CADETE
        16,17 -> Categories.JUNIOR
        18,21 -> Categories.SUB22
        in 22..99 -> Categories.SENIOR
        else -> Categories.BABYBASKET
    }
}

// Función que imprime el logo del equipo en la parte central superior junto a un título bicolor.
@Composable
fun TeamLogoChildScreens(titleText1: String, titleText2: String) {
    Image(painter = painterResource(
        id = R.drawable.logo_default),
        contentDescription = "Logo equipo",
        modifier = Modifier.size(140.dp)
    )
    Title2(text1 = titleText1, text2 = titleText2)
}

@Composable
private fun Title2(text1: String, text2: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text1,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 30.sp,
            modifier = Modifier.padding(top = 12.dp, bottom = 8.dp, end = 8.dp)
        )
        Text(
            text = text2,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 30.sp,
            modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)
        )
    }
}

// Funciones para convertir los nombres de los meses y dias al formato local del dispositivo
fun getMonth(month: Month?): String {
    return month?.getDisplayName(TextStyle.FULL, Locale.getDefault()).orEmpty().uppercase()
}

fun getDay(day: DayOfWeek):String {
    return day.getDisplayName(TextStyle.FULL, Locale.getDefault())
}

fun getDaysOfWeek(): List<String> {
    return DayOfWeek.entries.map {
        it.getDisplayName(TextStyle.SHORT, Locale.getDefault()).uppercase()
    }
}

// Botones para representar un partido con los datos más relevantes
@Composable
fun MatchButton(team1: TeamEntity, team2: TeamEntity, score: Score, date: String) {
    //Botón
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onBackground)
    ) {
        //Row imágenes y marcador
        MatchButtonScoreVs(score, date)
        // Row de nombres
        MatchButtonNames(team1, team2)
    }
}

@Composable
private fun MatchButtonNames(team1: TeamEntity, team2: TeamEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = team1.name,
            color = MaterialTheme.colorScheme.background,
            fontSize = 10.sp,
            modifier = Modifier
                .alpha(0.4f)
                .padding(start = 4.dp)
        )
        Text(
            text = team2.name,
            color = MaterialTheme.colorScheme.background,
            fontSize = 10.sp,
            modifier = Modifier
                .alpha(0.4f)
                .padding(start = 4.dp)
        )
    }
}

@Composable
private fun MatchButtonScoreVs(score: Score, date: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_default),
            contentDescription = "Team 1 logo",
            modifier = Modifier.size(80.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = score.getLocalScore().toString(),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 40.sp,
                    modifier = Modifier
                        .alpha(0.35f)
                )
                Text(
                    text = "VS",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp)
                )
                Text(
                    text = score.getVisitorScore().toString(),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 40.sp,
                    modifier = Modifier
                        .alpha(0.35f)
                )
            }
            Text(
                text = date,
                color = MaterialTheme.colorScheme.background,
                fontSize = 12.sp
            )
        }
        Image(
            painter = painterResource(id = R.drawable.logo_default),
            contentDescription = "Team 2 logo",
            modifier = Modifier.size(80.dp)
        )
    }
}

// Función que convierte las imagenes de un array de bytes en un bitmap
fun byteArrayToBitmap(byteArrayImage: ByteArray?): Bitmap? {
    // Con BitmapFactory creo un objeto bitmap partiendo de los bytes de la imagen
    if (byteArrayImage != null && byteArrayImage.isNotEmpty()) {
        Log.d("Functions_byteArrayToBitmap", "Array de bytes no nulo y no vacio")
        return BitmapFactory.decodeByteArray(byteArrayImage, 0, byteArrayImage.size)
    }
    Log.d("Functions_byteArrayToBitmap", "Array de bytes nulo o vacío")
    return null
}

// Función que redimensiona las iamgenes para guardarlas en la base de datos
@SuppressLint("Recycle")
fun imageResize(context: Context, uri: Uri): ByteArray? {
    val stream = context.contentResolver.openInputStream(uri)
    val baseImg = BitmapFactory.decodeStream(stream)

    if (baseImg != null) {
        val maxSize = Pair(1600, 1600)
        val width = if (baseImg.width > maxSize.first) maxSize.first else baseImg.width
        val height = if (baseImg.height > maxSize.second) maxSize.second else baseImg.height
        val scaledImg = Bitmap.createScaledBitmap(baseImg, width, height,true)
        val outputStream = ByteArrayOutputStream()
        scaledImg.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        return outputStream.toByteArray()
    }
    else return null
}