package com.example.basket4all.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.basket4all.R
import com.example.basket4all.common.enums.PlayerPositions
import com.example.basket4all.ui.theme.Basket4allTheme

@Composable
fun MatchScreen() {
    val list = listOf<String>("17 - 19", "20 - 20", "22 - 10", "8 - 10")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 8.dp, end = 8.dp, top = 16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { ScoreTitle(home = 67, visitor = 59, date = "13/12/2023") }
            item { PartialScores(scores = list)}
            item { FeaturePlayer(
                title = "Máximo anotador",
                name = "Rubén",
                role = PlayerPositions.ALAPIVOT.name,
                image = R.drawable.blank_profile_photo,
                number = 19
            ) }
            item { FeaturePlayer(
                title = "Máximo asistente",
                name = "Rubén",
                role = PlayerPositions.ALAPIVOT.name,
                image = R.drawable.blank_profile_photo,
                number = 19
            ) }
            item { FeaturePlayer(
                title = "Máximo reboteador",
                name = "Rubén",
                role = PlayerPositions.ALAPIVOT.name,
                image = R.drawable.blank_profile_photo,
                number = 19
            ) }
            item { FeaturePlayer(
                title = "Tirador",
                name = "Rubén",
                role = PlayerPositions.ALAPIVOT.name,
                image = R.drawable.blank_profile_photo,
                number = 19
            ) }
            item { FeaturePlayer(
                title = "Ladrón",
                name = "Rubén",
                role = PlayerPositions.ALAPIVOT.name,
                image = R.drawable.blank_profile_photo,
                number = 19
            ) }
        }
    }
}

@Composable
private fun ScoreTitle(home: Int, visitor: Int, date: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_default),
                contentDescription = "Default logo",
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = home.toString(),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "-",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = visitor.toString(),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Image(
                painter = painterResource(id = R.drawable.logo_default),
                contentDescription = "Default logo",
                modifier = Modifier.size(100.dp)
            )
        }
        Text(
            text = date,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }

}

@Composable
private fun PartialScores(scores: List<String>) {
    Text(
        text = "Parciales",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(top = 16.dp)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(2)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        scores.forEachIndexed { index, s ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp, start = 8.dp, end = 8.dp, top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${index + 1}",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = s,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "")
            }
        }
    }
}

@Composable
private fun FeaturePlayer(title: String, name: String, role: String, image: Int, number: Int?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(6)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = Modifier
                    .size(62.dp)
                    .padding(4.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.tertiary, CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                Text(
                    text = name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = role,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if (number != null) {
                Text(
                    text = number.toString(),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Basket4allTheme {
        MatchScreen()
    }
}