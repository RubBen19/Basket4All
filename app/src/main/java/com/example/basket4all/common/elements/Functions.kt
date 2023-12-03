package com.example.basket4all.common.elements

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