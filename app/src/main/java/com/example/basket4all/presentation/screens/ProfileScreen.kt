package com.example.basket4all.presentation.screens

import android.graphics.Bitmap
import android.util.Log
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.basket4all.R
import com.example.basket4all.common.messengers.SessionManager
import com.example.basket4all.common.elements.LoadScreen
import com.example.basket4all.common.elements.byteArrayToBitmap
import com.example.basket4all.data.local.entities.PlayerStats
import com.example.basket4all.presentation.activities.SelectorProfileImage
import com.example.basket4all.presentation.navigation.AppScreens
import com.example.basket4all.presentation.popup.UserInfoPopUp
import com.example.basket4all.presentation.viewmodels.db.CoachesViewModel
import com.example.basket4all.presentation.viewmodels.db.PlayerStatsViewModel
import com.example.basket4all.presentation.viewmodels.db.PlayersViewModel
import com.example.basket4all.presentation.viewmodels.screens.ProfileViewModel
import com.example.basket4all.presentation.viewmodels.screens.ProfileViewModelFactory
import com.example.basket4all.presentation.viewmodels.db.TeamViewModel

// Función encargada de representar la pantalla de perfil
@Composable
fun ProfileScreen(
    navController: NavHostController,
    playersViewModel: PlayersViewModel,
    coachesViewModel: CoachesViewModel,
    teamViewModel: TeamViewModel,
    playerStatsViewModel: PlayerStatsViewModel,
    userId: Int,
    playerOrCoach: Boolean?
) {
    val profileViewModel: ProfileViewModel = viewModel (
        factory = ProfileViewModelFactory(
            playersViewModel,
            coachesViewModel,
            playerStatsViewModel,
            teamViewModel,
            userId,
            playerOrCoach
        )
    )

    // Estado de la pantalla
    val screenUiState by profileViewModel.uiState.collectAsState()

    if (screenUiState.loading) {
        LoadScreen()
    }
    else {
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
                //Imagen de perfil junto al icono de información
                Profile(
                    pictureId = R.drawable.blank_profile_photo,
                    bitmap = byteArrayToBitmap(screenUiState.image),
                    userId = userId,
                    profileViewModel = profileViewModel,
                    playerOrCoach = playerOrCoach
                )
                //Muestra el nombre, apellido y dorsal
                Name(
                    name = screenUiState.username,
                    surname = screenUiState.surname,
                    number = if (screenUiState.number < 0) "C" else screenUiState.number.toString()
                )
                //Fila para agrupar los apartados de equipo y posiciones
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Top
                ) {
                    //Card del equipo
                    TeamCard(
                        teamName = screenUiState.team?.name ?: "",
                        league = screenUiState.team?.league ?: "",
                        category = screenUiState.team?.category?.name ?: "",
                        clickable = {
                            Log.d("ProfileScreen", AppScreens.TeamScreen.route+"/${screenUiState.team?.teamId}")
                            navController.navigate(AppScreens.TeamScreen.route+"/${screenUiState.team?.teamId}")
                        }
                    )
                    //Card de las posiciones
                    PositionsCard(positions = screenUiState.positions)
                }
                if (SessionManager.getInstance().getRole() == true && screenUiState.stats != null) {
                    //ButtonCard de las estadisticas del jugador
                    PlayerStatsCard(playerStats = screenUiState.stats!!)
                }
                else CoachStatsCard()
            }
            // Launcher del selector de imagen del perfil
            if (screenUiState.imageSelectorVisible) {
                SelectorProfileImage(profileViewModel = profileViewModel)
            }
            // Mostrar la información del usuario
            if (screenUiState.showInfo) {
                UserInfoPopUp(navController, profileViewModel, userId, playerOrCoach)
            }
        }
    }
}

// Función que representa la imagen junto al botón de más información
@Composable
private fun Profile (
    pictureId: Int,
    bitmap: Bitmap? = null,
    userId: Int,
    profileViewModel: ProfileViewModel,
    playerOrCoach: Boolean?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 48.dp, top = 20.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        //Imagen de perfil en bitmap
        if (bitmap != null) {
            Log.d("ProfileScreen", "Imagen de perfil: $bitmap")
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .clip(CircleShape)
                    .border(
                        width = 2.dp,
                        brush = Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.tertiary
                            )
                        ),
                        shape = CircleShape
                    )
                    .size(140.dp)
                    .clickable {
                        val sessionManager = SessionManager.getInstance()
                        if (userId == sessionManager.getUserId() &&
                            playerOrCoach == sessionManager.getRole()
                        ) {
                            profileViewModel.changeImageSelectorVisibility()
                        }
                    },
                contentScale = ContentScale.Crop
            )
        }
        // Imagen de perfil default
        else{
            Log.d("ProfileScreen", "Imagen de perfil por defecto")
            Image(
                painter = painterResource(id = pictureId),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .clip(CircleShape)
                    .border(
                        width = 2.dp,
                        brush = Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.tertiary
                            )
                        ),
                        shape = CircleShape
                    )
                    .size(140.dp)
                    .clickable {
                        if (userId == SessionManager
                                .getInstance()
                                .getUserId()
                        ) {
                            profileViewModel.changeImageSelectorVisibility()
                        }
                    }
            )
        }
        //Botón de info
        IconButton(
            onClick = { profileViewModel.changeVisibilityOfInfo() }
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "More info",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}
// Función para representar el nombre, apellido y dorsal con el formato deseado
@Composable
private fun Name(name: String, surname: String, number: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Nombre
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 36.sp
        )
        //Apellido
        Text(
            text = surname,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 24.sp
        )
        //Dorsal
        Text(
            text = number.toString(),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 60.sp
        )
    }
}

// Función para representar la clickable card del equipo al que pertenece junto a información básica
@Composable
private fun TeamCard(teamName: String, league: String, category: String, clickable: ()->Unit = {}) {
    Column(
        modifier = Modifier
            .border(
                width = 2.dp,
                brush = Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.tertiary
                    )
                ),
                shape = RectangleShape
            )
            .size(width = 160.dp, height = 240.dp)
            .clickable { clickable.invoke() },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Logo del equipo
        Image(
            painter = painterResource(id = R.drawable.tigers_cb_removebg_preview__1_),
            contentDescription = "Team picture",
            modifier = Modifier
                .clip(CircleShape)
                .size(100.dp)
        )
        //Nombre del equipo
        Text(
            text = teamName,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        //Liga
        Text(
            text = league,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        //Categoría
        Text(
            text = category,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 20.sp
        )
    }
}

// Función para representar la card de las posiciones del jugador
@Composable
private fun PositionsCard(positions: List<String>) {
    Column(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.onBackground,
                        MaterialTheme.colorScheme.onBackground
                    )
                ),
                shape = RectangleShape,
                alpha = 0.20f
            )
            .size(width = 160.dp, height = 240.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Icono
        Icon(
            painter = painterResource(id = R.drawable.sports_basketball),
            contentDescription = "Icon",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clip(CircleShape)
                .size(96.dp)
                .padding(top = 4.dp)
                .alpha(0.75f)
        )
        //Lista en columna de las posiciones
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            itemsIndexed(positions.sorted()) { i, position ->
                //Nombre de la posición
                Text(
                    text = position,
                    fontWeight = FontWeight.Bold,
                    color = if (i%2 == 0)MaterialTheme.colorScheme.onBackground
                    else MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp
                )
            }
        }
    }
}

// Función que muestra la clickable card de las estadísticas junto a una breve información de ellas
@Composable
private fun PlayerStatsCard(playerStats: PlayerStats) {
    Row(
        modifier = Modifier
            .size(width = 348.dp, height = 140.dp)
            .padding(top = 12.dp)
            .border(
                width = 2.dp,
                brush = Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.tertiary
                    )
                ),
                shape = RectangleShape
            )
            .clickable { },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Tamaño del texto utilizado
        val fontSize = 20.sp
        //Icono
        Icon(
            painter = painterResource(id = R.drawable.query_stats_24px__1_),
            contentDescription = "Icon",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .clip(CircleShape)
                .size(96.dp)
                .padding(4.dp)
                .alpha(0.35f)
        )
        //Columna con los nombres de las estadísticas que se van a mostrar
        Column {
            Text(
                text = "Puntos PP",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = fontSize,
            )
            Text(
                text = "Min PP",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = fontSize,
            )
            Text(
                text = "Asist PP",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = fontSize,
            )
            Text(
                text = "Reb PP",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = fontSize,
            )
        }
        //Valores de las estadísticas
        Column (
            modifier = Modifier.padding(end = 32.dp)
        ) {

            val games = if (playerStats.stats.games == 0) 1 else playerStats.stats.games
            //Puntos por partido
            Text(
                text = (playerStats.stats.shots.getPoints().div(games.toDouble())).toString(),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = fontSize,
            )
            //Minutos por partido
            Text(
                text = (playerStats.stats.minutes.div(games)).toString(),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = fontSize,
            )
            //Asistencias por partido
            Text(
                text = (playerStats.stats.lastPass.getAssist().toDouble().div(games)).toString(),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = fontSize,
            )
            //Rebotes por partido
            Text(
                text = (playerStats.stats.rebounds.getRebounds().toDouble().div(games).toString()),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = fontSize,
            )
        }
    }
}

// Función que muestra la clickable card de las estadísticas junto a una breve información de ellas
@Composable
private fun CoachStatsCard() {
    Row(
        modifier = Modifier
            .size(width = 348.dp, height = 140.dp)
            .padding(top = 12.dp)
            .border(
                width = 2.dp,
                brush = Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.tertiary
                    )
                ),
                shape = RectangleShape
            )
            .clickable { },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Tamaño del texto utilizado
        val fontSize = 20.sp
        //Icono
        Icon(
            painter = painterResource(id = R.drawable.query_stats_24px__1_),
            contentDescription = "Icon",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .clip(CircleShape)
                .size(96.dp)
                .padding(4.dp)
                .alpha(0.35f)
        )
        //Columna con los nombres de las estadísticas que se van a mostrar
        Column {
            Text(
                text = "Partidos ganados",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = fontSize,
            )
            Text(
                text = "Partidos perdidos",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = fontSize,
            )
            Text(
                text = "% De victoria",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = fontSize,
            )
        }
        //Valores de las estadísticas
        Column (
            modifier = Modifier.padding(end = 32.dp)
        ) {

            //Puntos por partido
            Text(
                text = "4",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = fontSize,
            )
            //Minutos por partido
            Text(
                text = "1",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = fontSize,
            )
            //Minutos por partido
            Text(
                text = "75%",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = fontSize,
            )
        }
    }
}