package com.example.basket4all.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.basket4all.R
import com.example.basket4all.ui.theme.Basket4allTheme

// Variables relacionadas con el tamaño de la fuente
private const val FONT_SIZE: Int = 16

/**
 * Función composable principal de la pantalla de Screen
 */
@Composable
fun Log2Screen() {
    // Se ocupa toda la pantalla y se establece el fondo
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
    ) {
        // Se muestra el contenido de la pantalla
        LogContent (
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
private fun LogContent(modifier: Modifier = Modifier) {
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
            modifier = Modifier
                .padding(top = 72.dp)
                .align(Alignment.CenterHorizontally)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.surface
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                .size(272.dp)
                .border(
                    width = 4.dp,
                    brush = Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.surface
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
                click = { /*TODO*/ },
                fontSize = FONT_SIZE,
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
private fun LogInFormulary(modifier: Modifier = Modifier) {
    // Variables relacionadas con los campos del formulario
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var hidden by remember { mutableStateOf(true) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text(
                    text = "E-Mail",
                    fontWeight = FontWeight.Light
                ) },
            singleLine = true,
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally)
                .size(height = 60.dp, width = 232.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text(
                    text = "Contraseña",
                    fontWeight = FontWeight.Light
                ) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            visualTransformation = if(hidden) PasswordVisualTransformation ()
            else VisualTransformation.None,
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
                .padding(top = 12.dp)
                .align(Alignment.CenterHorizontally)
                .size(height = 60.dp, width = 232.dp)
        )

        LogInButton(
            text = "Iniciar Sesión",
            click = { /*TODO*/ },
            fontSize = FONT_SIZE,
            modifier = Modifier
                .padding(top = 24.dp)
                .size(152.dp, 52.dp)
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

        TextWithButtonText(
            text = "",
            buttonText = "He olvidado mi contraseña",
            click = { /*TODO*/ },
            fontSize = FONT_SIZE,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

/**
 *  Función composable encargada de dibujar un botón.
 *  Dicho botón mostrará el texto (en negrita) indicado en el campo "text".
 *  Además podrá realizar la acción (o acciones) indicadas en click cuando el usuario lo presione
 **/
@Composable
private fun LogInButton(text: String, click: () -> Unit, fontSize: Int,
                        modifier: Modifier = Modifier) {
    TextButton(
        modifier = modifier,
        onClick = { click.invoke() }
    ) {
        Text(
            text = text,
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun TextWithButtonText(text: String, buttonText: String, click: () -> Unit,
                               fontSize: Int, modifier: Modifier = Modifier) {
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
                color = MaterialTheme.colorScheme.tertiary
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

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun Log2Preview() {
    Basket4allTheme {
        Log2Screen()
    }
}