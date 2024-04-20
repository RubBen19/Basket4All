package com.example.basket4all.presentation.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.basket4all.R
import com.example.basket4all.common.enums.Categories
import com.example.basket4all.common.enums.PlayerPositions
import com.example.basket4all.ui.theme.Basket4allTheme

@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Profile(pictureId = R.drawable.blank_profile_photo)
            Name(name = "James", username = "Williams", number = 1)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Top
            ) {
                TeamCard(teamName = "TIGERS CB", league = "EBA", category = Categories.SUB22.name)
                val positions = listOf(PlayerPositions.PIVOT, PlayerPositions.ALAPIVOT,
                    PlayerPositions.ALERO, PlayerPositions.BASE, PlayerPositions.ESCOLTA)
                PositionsCard(positions = positions)
            }
            Row(
                modifier = Modifier
                    .size(width = 352.dp, height = 160.dp)
                    .padding(top = 16.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.onBackground,
                                MaterialTheme.colorScheme.background
                            )
                        ),
                        shape = RoundedCornerShape(6),
                        alpha = 0.65f
                    )
                    .border(
                        width = 4.dp,
                        brush = Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.tertiary
                            )
                        ),
                        shape = RoundedCornerShape(6)
                    )
                    .clickable { },
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.query_stats_24px__1_),
                    contentDescription = "Icon",
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(96.dp)
                        .padding(4.dp)
                )
                Column(
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = "Points PP",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = "Min PP",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = "Asisst PP",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = "Reb PP",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 20.sp,
                    )
                }
                Column (
                    modifier = Modifier.padding(start = 20.dp)
                ) {
                    Text(
                        text = "12.4",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = "20",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = "2",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = "4",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 20.sp,
                    )
                }
            }
        }
    }
}
@Composable
private fun Profile (pictureId: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 44.dp, top = 24.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        //Profile image
        Image(
            painter = painterResource(id = pictureId),
            contentDescription = "Profile picture",
            modifier = Modifier
                .clip(CircleShape)
                .border(
                    width = 4.dp,
                    brush = Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.tertiary
                        )
                    ),
                    shape = CircleShape
                )
                .size(160.dp)
        )
        IconButton(
            onClick = { /*TODO*/ }
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "More info",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(44.dp)
            )
        }
    }
}
@Composable
private fun Name(name: String, username: String, number: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 40.sp
        )
        Text(
            text = username,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 28.sp
        )
        Text(
            text = number.toString(),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 72.sp
        )
    }
}

@Composable
private fun TeamCard(teamName: String, league: String, category: String) {
    Column(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.onBackground,
                        MaterialTheme.colorScheme.background
                    )
                ),
                shape = RoundedCornerShape(6),
                alpha = 0.65f
            )
            .border(
                width = 4.dp,
                brush = Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.tertiary
                    )
                ),
                shape = RoundedCornerShape(6)
            )
            .size(width = 168.dp, height = 252.dp)
            .clickable { },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.tigers_cb_removebg_preview__1_),
            contentDescription = "Profile picture",
            modifier = Modifier
                .clip(CircleShape)
                .size(120.dp)
        )
        Text(
            text = teamName,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 24.sp,
        )
        Text(
            text = league,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = category,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 20.sp
        )
    }
}

@Composable
private fun PositionsCard(positions: List<PlayerPositions>) {
    Column(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.onBackground,
                        MaterialTheme.colorScheme.background
                    )
                ),
                shape = RoundedCornerShape(6),
                alpha = 0.65f
            )
            .border(
                width = 4.dp,
                brush = Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.tertiary
                    )
                ),
                shape = RoundedCornerShape(6)
            )
            .size(width = 168.dp, height = 252.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.sports_basketball),
            contentDescription = "Icon",
            tint = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .clip(CircleShape)
                .size(96.dp)
                .padding(top = 4.dp)
        )
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            itemsIndexed(positions.sorted()) { i, position ->
                Text(
                    text = position.toString(),
                    fontWeight = FontWeight.Bold,
                    color = if (i%2 == 0)MaterialTheme.colorScheme.onBackground
                    else MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(showBackground = true)
@Composable
private fun Preview() {
    Basket4allTheme {
        ProfileScreen()
    }

}