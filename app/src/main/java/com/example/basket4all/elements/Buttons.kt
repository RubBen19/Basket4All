package com.example.basket4all.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.basket4all.R

@Composable
fun TextButtonMain (text: String, click: () -> Any, alignment: Alignment.Horizontal) {
    Column (modifier = Modifier.fillMaxWidth()) {
        TextButton(
            onClick = {
                click.invoke()
            },
            modifier = Modifier
                .size(150.dp, 50.dp)
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(15.dp)
                )
                .align(alignment)
                .border(
                    width = 3.dp,
                    brush = Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.tertiary
                        )
                    ),
                    shape = RoundedCornerShape(15.dp)
                )
        ) {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TextButtonOnlyText (text: String, click: () -> Unit?, fontSize: Int) {
    TextButton(onClick = { click.invoke() }
    ) {
        Text(text = text, fontSize = fontSize.sp)
    }
}

@Composable
fun IconButtonBottomBar (click: () -> Unit?, buttonSize: Dp, icon: Int, iconSize: Dp,
                         text: String, description: String)
{
    IconButton(onClick = {click .invoke()},
        modifier = Modifier.size(buttonSize)
    ) {
        Column (modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Icon(painter = painterResource(icon),
                contentDescription = description,
                modifier = Modifier
                    .size(iconSize),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(text = text, color = MaterialTheme.colorScheme.onBackground)
        }
    }
}