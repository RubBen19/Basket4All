package com.example.basket4all.presentation.popup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.basket4all.common.messengers.SessionManager
import com.example.basket4all.presentation.navigation.AppScreens
import com.example.basket4all.presentation.viewmodels.screens.ProfileViewModel
import java.time.format.DateTimeFormatter

@Composable
fun UserInfoPopUp(navController: NavHostController, profileViewModel: ProfileViewModel, userId: Int, role: Boolean?) {
    // Datos del usuario
    val userInfo by profileViewModel.user.observeAsState()
    val sessionMng = SessionManager.getInstance()

    AlertDialog(
        shape = RectangleShape,
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = { profileViewModel.changeVisibilityOfInfo() },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = { profileViewModel.changeVisibilityOfInfo() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(80.dp)
                    )
                }
            }
        },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = userInfo?.name ?: "",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = " ${userInfo?.surname1 ?: ""} ${userInfo?.surname2 ?: ""}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                // Fecha de nacimiento
                Row {
                    Text(
                        text = "Fecha de nacimiento: ",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = userInfo?.birthdate?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) ?: "",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                // Correo electrónico
                Row {
                    Text(
                        text = "E-mail: ",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = userInfo?.email ?: "",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                if (sessionMng.getUserId()==userId && sessionMng.getRole() == role) {
                    // Botón editar perfil
                    Button(
                        onClick = { navController.navigate(AppScreens.EditUserInfoScreen.route) },
                        colors = ButtonDefaults
                            .buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(12),
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = "Editar perfil",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    )
}