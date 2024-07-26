package com.example.basket4all.presentation.activities

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.basket4all.common.elements.imageResize
import com.example.basket4all.presentation.viewmodels.screens.ProfileViewModel

@Composable
fun SelectorProfileImage(profileViewModel: ProfileViewModel) {
    var uri by remember { mutableStateOf<Uri?>(null) }
    val profileImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        uri = it
    }
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(0.5f)
                .align(Alignment.Center)
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
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .size(140.dp)
                        .clip(CircleShape)
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
                        if (uri!=null) {
                            val byteArray = imageResize(context, uri!!)
                            if (byteArray != null) {
                                profileViewModel.changeProfileImage(byteArray)
                            }
                        }
                        profileViewModel.changeImageSelectorVisibility()
                    },
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = "Guardar")
                }
            }
        }
    }

}