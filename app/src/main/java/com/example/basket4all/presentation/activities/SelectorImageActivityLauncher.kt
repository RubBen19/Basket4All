package com.example.basket4all.presentation.activities

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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

    AlertDialog(
        onDismissRequest = { profileViewModel.changeImageSelectorVisibility() },
        confirmButton = {
            IconButton(
                onClick = {
                    if (uri!=null) {
                        val byteArray = imageResize(context, uri!!)
                        if (byteArray != null) {
                            profileViewModel.changeProfileImage(byteArray)
                        }
                    }
                    profileViewModel.changeImageSelectorVisibility()
                },
                modifier = Modifier
                    .background (
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Guardar",
                    tint = Color.White
                )
            }
        },
        dismissButton = {
            IconButton(
                onClick = {
                    profileImageLauncher
                        .launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                },
                modifier = Modifier
                    .background (
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar",
                    tint = Color.White,
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
                )
            }
        },
        title = {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = uri,
                    contentDescription = "Imagen de perfil seleccionada",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(140.dp)
                        .background(Color.White, CircleShape)
                )
            }
        }
    )
}