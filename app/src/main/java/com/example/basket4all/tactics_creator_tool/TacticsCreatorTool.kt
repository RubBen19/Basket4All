package com.example.basket4all.tactics_creator_tool

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

@Composable
fun BoardScreen() {
    val localColor = MaterialTheme.colorScheme.primary
    val visitorColor = Color.Gray
    val players = remember { mutableStateListOf(
        Player2D(1, localColor, 25f, 825f),
        Player2D(2, localColor, 25f, 925f),
        Player2D(3, localColor, 25f, 1025f),
        Player2D(4, localColor, 25f, 1125f),
        Player2D(5, localColor, 25f, 1225f),
        Player2D(1, visitorColor, 900f, 825f),
        Player2D(2, visitorColor, 900f, 925f),
        Player2D(3, visitorColor, 900f, 1025f),
        Player2D(4, visitorColor, 900f, 1125f),
        Player2D(5, visitorColor, 900f, 1225f)
    ) }

    CourtDraw(players) { id, offset ->
        val index = players.indexOfFirst { it.id == id }
        if (index != -1) {
            players[index] = players[index].copy(x = offset.x, y = offset.y)
        }
    }
}
