package com.example.basket4all.common.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.basket4all.R
import com.example.basket4all.common.enums.Categories
import java.time.LocalDate
import java.time.LocalDateTime

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
        id = R.drawable.tigers_cb_removebg_preview__1_),
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