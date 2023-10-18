package com.example.basket4all.screens

import android.annotation.SuppressLint
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import com.example.basket4all.R
import com.example.basket4all.elements.TextButtonMain
import com.example.basket4all.elements.TextButtonOnlyText
import com.example.basket4all.navigation.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LogScreen(navController: NavHostController) {
    Scaffold {
        BodyContent(navController)
    }
}

@Composable
private fun BodyContent (navController: NavHostController){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            Brush.verticalGradient(
                listOf(
                    MaterialTheme.colorScheme.background,
                    MaterialTheme.colorScheme.surface
                )
            )
        )
    ){
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Logo()
            Spacer(modifier = Modifier.height(50.dp))
            Formulary(navController)
            GoToRegister(navController)
        }
    }
}

/**
 * Logo ()
 * Se encarga de proyectar el logo de la app en la pantalla-
 */
@Composable
private fun Logo () {
    Image(
        painter = painterResource(
            id = if(isSystemInDarkTheme())R.drawable.black_removebg_preview
            else R.drawable.white_removebg_preview
        ),
        contentDescription = "App Logo",
        modifier = Modifier.size(250.dp)
    )
}

/**
 * Formulary (navController: NavHostController)
 * Se encarga de la representación gráfica de la entrada del usuario para introducir sus credenciales
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Formulary (navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var hidden by remember { mutableStateOf(true) }

    Column (modifier = Modifier
        .background(
            brush = Brush.verticalGradient(
                listOf(MaterialTheme.colorScheme.secondary, MaterialTheme.colorScheme.surface)
            ), shape = RoundedCornerShape(15.dp)
        )
        .size(270.dp)
        .border(
            width = 3.dp,
            brush = Brush.verticalGradient(
                listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.surface)
            ),
            shape = RoundedCornerShape(15.dp)
        )
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = { Text(text = "E-Mail",
                fontWeight = FontWeight.Light) },
            singleLine = true,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(height = 60.dp, width = 225.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = { Text(text = "Contraseña",
                fontWeight = FontWeight.Light,) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            visualTransformation = if(hidden) PasswordVisualTransformation ()
            else VisualTransformation.None,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(height = 60.dp, width = 225.dp),
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
        )
        Spacer(modifier = Modifier.height(35.dp))
        TextButtonMain(
            text = "Iniciar Sesión",
            click = {
                navController.popBackStack()
                navController.navigate(route = AppScreens.FirstScreen.route) },
            Alignment.CenterHorizontally
        )
    }
}

/**
 * GoToRegister (navController: NavHostController)
 * Escribe la línea de texto inferior y su respectivo botón para permitir al usuario crear una cuenta
 */
@Composable
private fun GoToRegister (navController: NavHostController) {
    val fontSize = 15
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 18.dp)
        ){
            Text(text = "¿Aún no tienes una cuenta?", fontSize = fontSize.sp)
            TextButtonOnlyText(
                text = "Crea una aqui",
                click = { navController.navigate(route = AppScreens.RegisterScreen.route) },
                fontSize = fontSize
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LogPreview() {

}