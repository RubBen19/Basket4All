package com.example.basket4all.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.basket4all.R
import com.example.basket4all.data.local.entities.CoachEntity
import com.example.basket4all.data.local.entities.PlayerEntity
import com.example.basket4all.presentation.navigation.AppScreens
import com.example.basket4all.presentation.viewmodels.CoachesViewModel
import com.example.basket4all.presentation.viewmodels.LoginViewModel
import com.example.basket4all.presentation.viewmodels.PlayersViewModel

/**
 * ARCHIVO: LogScreen.kt
 * FUNCIÓN: El objetivo de este archivo es la funcionalidad de la pantalla de iniciar sesión
 */

// Variables relacionadas con el tamaño de la fuente
private const val FONT_SIZE: Int = 16

/**
 * Función composable principal de la pantalla de inicio de sesión
 */
@Composable
fun LogScreen(
    navController: NavHostController,
    playersViewModel: PlayersViewModel,
    coachesViewModel: CoachesViewModel
) {
    // Se ocupa toda la pantalla y se establece el fondo
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Se muestra el contenido de la pantalla
        LogContent (
            navController = navController,
            playersViewModel = playersViewModel,
            coachesViewModel = coachesViewModel,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.TopCenter)
        )
    }

}

/**
 * Funcion composable contenedora del contenido de la pantalla de Log.
 * Se encarga de dibujar la pantalla de Log invocando a todos los elementos necesarios.
 * Mediante su agrupación y gestión se logra el funcionamiento deseado para la screen.
 **/
@Composable
private fun LogContent(navController: NavHostController, playersViewModel: PlayersViewModel,
                       coachesViewModel: CoachesViewModel, modifier: Modifier = Modifier) {
    // IDs de las imágenes que se utilizarán como logo de la app
    val lightLogoID = R.drawable.white_removebg_preview
    val darkLogoID = R.drawable.black_removebg_preview
    Column(
        modifier = modifier
    ) {
        // Logo de la App
        Logo(
            lightLogoID = lightLogoID,
            darkLogoID = darkLogoID,
            modifier = Modifier
                .padding(top = 80.dp)
                .size(252.dp)
                .align(Alignment.CenterHorizontally)
        )
        // Formulario para el inicio de sesión
        LogInFormulary(
            navController = navController,
            playersViewModel = playersViewModel,
            coachesViewModel = coachesViewModel,
            modifier = Modifier
                .padding(top = 72.dp)
                .align(Alignment.CenterHorizontally)
                .background(MaterialTheme.colorScheme.background)
                .size(272.dp)
                .border(
                    width = 4.dp,
                    brush = Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.background
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
        )
        Column (
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 8.dp)
        ){
            TextWithButtonText(
                text = "¿Aún no tienes una cuenta?",
                buttonText = "Crea una aquí",
                click = {
                    navController.navigate(route = AppScreens.RegisterScreen.route)
                },
                fontSize = FONT_SIZE,
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

/**
 * Funcion composable contenedora del contenido del formulario de inicio de sesión.
 * Se encarga de dibujar los campos de datos y botones, invocando a todos los elementos necesarios.
 * Mediante su agrupación y gestión se logra el funcionamiento deseado.
 **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LogInFormulary(navController: NavHostController, playersViewModel: PlayersViewModel,
                           coachesViewModel: CoachesViewModel, modifier: Modifier = Modifier) {
    // Variable para ocultar la contraseña
    var hidden by remember { mutableStateOf(true) }
    var email: String by remember { mutableStateOf("") }
    var password: String by remember { mutableStateOf("") }
    // Observa el LiveData para el jugador con el correo electrónico dado
    val playerLiveData = playersViewModel.getByEmail(email)
    val playerState by playerLiveData.observeAsState(initial = null)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        // Campo de texto para pedir el e-mail
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text(
                    text = "E-Mail"
                ) },
            singleLine = true,
            modifier = Modifier
                .padding(top = 4.dp)
                .align(Alignment.CenterHorizontally)
                .size(height = 60.dp, width = 232.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground)
        )
        // Campo de texto para pedir la contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text(
                    text = "Contraseña"
                ) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,

            ),
            singleLine = true,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
            // Trasnformacion del campo para visualizar el texto introducido
            visualTransformation = if(hidden) PasswordVisualTransformation ()
            else VisualTransformation.None,
            // Cambio en el icono de visualizar contraseña
            trailingIcon = {
                IconButton(onClick = { hidden = !hidden }) {
                    Icon(
                        painterResource(
                            id = if (hidden) R.drawable.visibility_off
                            else R.drawable.visibility_on
                        ),
                        contentDescription = "Visibility",
                        tint = if (hidden) Color.Gray
                        else MaterialTheme.colorScheme.primary
                    )
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(height = 60.dp, width = 232.dp)
        )
        // Botón de iniciar sesión
        LogInButton(
            text = "Iniciar Sesión",
            click = {
                val player = playerLiveData.value
                val coach = coachesViewModel.getByEmail(email)
                if(player != null && player.user.password == password) {
                    navController.navigate(AppScreens.FirstScreen.route)
                }
                else if(coach != null && coach.user.password == password) {
                    navController.navigate(AppScreens.FirstScreen.route)
                }
            },
            fontSize = FONT_SIZE,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 24.dp)
                .size(160.dp, 52.dp)
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(16.dp)
                )
                .border(
                    width = 4.dp,
                    brush = Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.tertiary
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
        )

        // Botón para ir al campo de contraseña olvidada
        TextWithButtonText(
            text = "",
            buttonText = "He olvidado mi contraseña",
            click = { /*TODO*/ },
            fontSize = FONT_SIZE,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
private fun LogInError(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Usuario o contraseña incorrecto\n" +
                        "Prueba otra vez o crea una cuenta",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

/**
 *  Función composable encargada de dibujar un botón.
 *  Dicho botón mostrará el texto (en negrita) indicado en el campo "text".
 *  Además podrá realizar la acción (o acciones) indicadas en click cuando el usuario lo presione
 **/
@Composable
private fun LogInButton(text: String, click: () -> Unit, fontSize: Int, color: Color,
                        modifier: Modifier = Modifier) {
    TextButton(
        modifier = modifier,
        onClick = { click.invoke() }
    ) {
        Text(
            text = text,
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Composable
private fun TextWithButtonText(text: String, buttonText: String, click: () -> Unit,
                               fontSize: Int, color: Color, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = fontSize.sp
        )
        TextButton(
            onClick = { click.invoke() }
        ) {
            Text(
                text = buttonText,
                fontSize = fontSize.sp,
                color = color
            )
        }
    }

}

/* Función composable encargada de proyectar el logo de la app */
@Composable
private fun Logo(lightLogoID: Int, darkLogoID: Int, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(
            id = if (isSystemInDarkTheme()) darkLogoID else lightLogoID
        ),
        contentDescription = "Logo de la app en la pantalla de log",
        modifier = modifier
    )
}
