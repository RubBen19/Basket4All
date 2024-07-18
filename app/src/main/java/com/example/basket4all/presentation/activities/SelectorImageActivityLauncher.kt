package com.example.basket4all.presentation.activities

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.basket4all.presentation.viewmodels.screens.ProfileViewModel

@Composable
fun SelectorProfileImage(profileViewModel: ProfileViewModel) {
    var uri by remember { mutableStateOf<Uri?>(null) }
    val profileImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        uri = it
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(0.50f)
            .background(MaterialTheme.colorScheme.background)
            .border(2.dp, MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            AsyncImage(
                model = uri,
                contentDescription = "Imagen de perfil seleccionada",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        item {
            TextButton(
                onClick = { profileImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = "Selecciona tu foto de perfil")
            }
        }
        item {
            TextButton(
                onClick = {
                    if (uri!=null) profileViewModel.changeProfileImage(uri.toString().toByteArray())
                    profileViewModel.changeImageSelectorVisibility()
                },
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = "Guardar")
            }
        }
    }
}