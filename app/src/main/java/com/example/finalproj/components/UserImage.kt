package com.example.finalproj.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.finalproj.database.DatabaseKeys
import com.example.finalproj.database.DatabaseManager
import com.example.finalproj.database.StorageManager
import com.example.finalproj.models.User


@Composable
fun UserImage(
    modifier: Modifier = Modifier,
    user: User,
    contentDescription: String?,
    elevation: Dp = 0.dp
) {
    var imageUrl by remember { mutableStateOf(user.imageUrl) }

    Eat2FitSurface(
        color = Color.LightGray,
        elevation = elevation,
        shape = CircleShape,
        modifier = modifier
    ) {
        MyImageArea(
            uri = imageUrl,
            directory = LocalContext.current.cacheDir,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            onSetUri = { uri ->
                val newImageUrl = uri.toString()
                imageUrl = newImageUrl

                StorageManager.uploadImage(uri, onSuccess = {
                    DatabaseManager.updateUserKey(DatabaseKeys.IMAGE_URL, it)
                }, onFailure = {
                })
            },
            onRemoveUri = {
                imageUrl = null
                DatabaseManager.updateUserKey(DatabaseKeys.IMAGE_URL, null)

            }
        )
    }
}



