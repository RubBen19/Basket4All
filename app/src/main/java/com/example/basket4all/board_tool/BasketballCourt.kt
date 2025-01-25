package com.example.basket4all.board_tool

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun CourtDraw(playersList: List<Player2D>, playerMove: (Int, Offset) -> Unit) {
    val lineColor = MaterialTheme.colorScheme.primary
    val basketBoardColor = MaterialTheme.colorScheme.onBackground
    val linesWidth = 4.dp

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ) {
        // Uso de Canvas para dibujar la pista de juego
        // Línea y círculo central
        DrawCourtLines(lineColor,linesWidth)
        // Líneas de triple inferior y superior
        Draw3Lines(lineColor,linesWidth)
        // Líneas de la zona inferior y superior
        DrawZoneLines(lineColor, linesWidth)
        // Fichas de los jugadores
        playersList.forEach { player2D ->
            ScrollPlayer(player2D, playerMove)
        }
        // Balón
        ScrollableBall(playerMove)
        // Canasta inferior y superior
        DrawBaskets(basketBoardColor, linesWidth)
    }
}

@Composable
private fun DrawBaskets(basketBoardColor: Color, linesWidth: Dp) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val radius = size.width * 0.16f
        /* Canastas */
        // Tablero inferior
        drawLine(
            color = basketBoardColor,
            start = Offset(size.width.div(2) - size.width.div(10), size.height - 25f),
            end = Offset(size.width.div(2) + size.width.div(10),size.height - 25f),
            strokeWidth = linesWidth.toPx()
        )
        // Aro inferior
        drawCircle(
            color = basketBoardColor,
            center = Offset(size.width.div(2), size.height - 75f),
            radius = radius.div(4),
            style = Stroke(width = linesWidth.toPx())
        )
        // Tablero superior
        drawLine(
            color = basketBoardColor,
            start = Offset(size.width.div(2) - size.width.div(10), 25f),
            end = Offset(size.width.div(2) + size.width.div(10),25f),
            strokeWidth = linesWidth.toPx()
        )
        // Aro superior
        drawCircle(
            color = basketBoardColor,
            center = Offset(size.width.div(2), 75f),
            radius = radius.div(4),
            style = Stroke(width = linesWidth.toPx())
        )
    }
}

@Composable
private fun DrawCourtLines(lineColor: Color, linesWidth: Dp) {
   Canvas(modifier = Modifier.fillMaxSize()) {
       val radius = size.width * 0.16f
       // Linea central
       drawLine(
           color = lineColor,
           start = Offset(0f, size.height.div(2)),
           end = Offset(size.width, size.height.div(2)),
           strokeWidth = linesWidth.toPx()
       )
       // Círculo central
       drawCircle(
           color = lineColor,
           center = Offset(size.width.div(2), size.height.div(2)),
           radius = radius,
           style = Stroke(width = linesWidth.toPx())
       )
   }
}

@Composable
private fun DrawZoneLines(lineColor: Color, linesWidth: Dp) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        // Linea zona lateral izquierda superior
        drawLine(
            color = lineColor,
            start = Offset(size.width.div(3), 0f),
            end = Offset(size.width.div(3), size.height.div(8)),
            strokeWidth = linesWidth.toPx()
        )
        // Dibujar el arco de la zona superior
        drawArc(
            color = lineColor,
            startAngle = 180f,
            sweepAngle = -180f,
            useCenter = false,
            topLeft = Offset(size.width.div(3), size.height.div(24)),
            size = Size(size.width.div(3), size.height.div(6)),
            style = Stroke(width = linesWidth.toPx())
        )
        // Linea zona lateral derecha superior
        drawLine(
            color = lineColor,
            start = Offset(size.width - size.width.div(3), 0f),
            end = Offset(size.width - size.width.div(3), size.height.div(8)),
            strokeWidth = linesWidth.toPx()
        )
        // Línea de tiro libre superior
        drawLine(
            color = lineColor,
            start = Offset(size.width.div(3), size.height.div(8)),
            end = Offset(size.width - size.width.div(3), size.height.div(8)),
            strokeWidth = linesWidth.toPx()
        )

        // Linea zona lateral izquierda superior
        drawLine(
            color = lineColor,
            start = Offset(size.width.div(3), size.height),
            end = Offset(size.width.div(3), size.height - size.height.div(8)),
            strokeWidth = linesWidth.toPx()
        )
        // Dibujar el arco de la zona superior
        drawArc(
            color = lineColor,
            startAngle = -180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(size.width.div(3), size.height - size.height.div(4.8f)),
            size = Size(size.width.div(3), size.height.div(6)),
            style = Stroke(width = linesWidth.toPx())
        )
        // Linea zona lateral derecha superior
        drawLine(
            color = lineColor,
            start = Offset(size.width - size.width.div(3), size.height),
            end = Offset(size.width - size.width.div(3), size.height - size.height.div(8)),
            strokeWidth = linesWidth.toPx()
        )
        // Línea de tiro libre superior
        drawLine(
            color = lineColor,
            start = Offset(size.width.div(3), size.height - size.height.div(8)),
            end = Offset(size.width - size.width.div(3), size.height - size.height.div(8)),
            strokeWidth = linesWidth.toPx()
        )
    }
}

@Composable
private fun Draw3Lines(lineColor: Color, linesWidth: Dp) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        // Linea triple lateral izquierda superior
        drawLine(
            color = lineColor,
            start = Offset(size.width.div(8), 0f),
            end = Offset(size.width.div(8), size.height.div(12)),
            strokeWidth = linesWidth.toPx()
        )
        // Dibujar el arco de tres puntos superior
        drawArc(
            color = lineColor,
            startAngle = 180f,
            sweepAngle = -180f,
            useCenter = false,
            topLeft = Offset(size.width.div(8), -size.height.div(12)),
            size = Size(size.width - size.width.div(4), size.height.div(3)),
            style = Stroke(width = linesWidth.toPx())
        )
        // Linea triple lateral derecha superior
        drawLine(
            color = lineColor,
            start = Offset(size.width - size.width.div(8), 0f),
            end = Offset(size.width - size.width.div(8), size.height.div(12)),
            strokeWidth = linesWidth.toPx()
        )

        // Linea triple lateral izquierda inferior
        drawLine(
            color = lineColor,
            start = Offset(size.width.div(8), size.height),
            end = Offset(size.width.div(8), size.height - size.height.div(12)),
            strokeWidth = linesWidth.toPx()
        )

        // Dibujar el arco de tres puntos inferior
        drawArc(
            color = lineColor,
            startAngle = -180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(size.width.div(8), size.height - size.height.div(4)),
            size = Size(size.width - size.width.div(4), size.height.div(3)),
            style = Stroke(width = linesWidth.toPx())
        )
        // Linea triple lateral derecha inferior
        drawLine(
            color = lineColor,
            start = Offset(size.width - size.width.div(8), size.height),
            end = Offset(size.width - size.width.div(8), size.height - size.height.div(12)),
            strokeWidth = linesWidth.toPx()
        )
    }
}

@Composable
private fun ScrollPlayer(player2D: Player2D, playerMove: (Int, Offset) -> Unit) {
    var offset by remember { mutableStateOf(Offset(player2D.x, player2D.y)) }

    Box(
        modifier = Modifier
            .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
            .background(color = player2D.color, shape = CircleShape)
            .border(2.dp, color = Color.Black, shape = CircleShape)
            .size(44.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offset = Offset(offset.x + dragAmount.x, offset.y + dragAmount.y)
                    playerMove(player2D.id, offset)
                }
            }
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = player2D.id.toString(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ScrollableBall(ballMove: (Int, Offset) -> Unit) {
    var offset by remember { mutableStateOf(Offset(0f, 0f)) }
    val ballSize = 35f
    // Dibujo del balón
    Canvas(
        modifier = Modifier
            .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
            .size((ballSize * 2).dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offset += dragAmount
                    ballMove(0, offset)
                }
            }
    ) {
        // Esfera
        drawCircle(color = Color(0xFFFFA500), center =  center, radius = ballSize)
        // Líneas
        drawLine(
            color = Color.Black,
            start = Offset(center.x - ballSize, center.y),
            end = Offset(center.x + ballSize, center.y),
            strokeWidth = 3f
        )
        drawArc(
            color = Color.Black,
            startAngle = 360f,
            sweepAngle = -180f,
            useCenter = false,
            topLeft = Offset(center.x - ballSize*0.97f, center.y - (ballSize*1.4f)/2),
            size = Size(ballSize*0.97f*2, ballSize*1.4f),
            style = Stroke(width = 3f)
        )
        drawArc(
            color = Color.Black,
            startAngle = 360f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(center.x - ballSize*0.97f, center.y - (ballSize*1.4f)/2),
            size = Size(ballSize*0.97f*2, ballSize*1.4f),
            style = Stroke(width = 3f)
        )
        drawArc(
            color = Color.Black,
            startAngle = 45f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(center.x - ballSize, center.y - ballSize),
            size = Size(ballSize*2, ballSize*2),
            style = Stroke(width = 4f)
        )
        drawArc(
            color = Color.Black,
            startAngle = 225f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(center.x - ballSize, center.y - ballSize),
            size = Size(ballSize*2, ballSize*2),
            style = Stroke(width = 4f)
        )
    }
}
