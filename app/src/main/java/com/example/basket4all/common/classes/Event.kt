package com.example.basket4all.common.classes

import com.example.basket4all.common.enums.EventType

data class Event(
    val type: EventType,
    val description: String,
    val place: String = "",
    val hour: String = ""
)
