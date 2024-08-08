package com.example.finalproj.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.finalproj.R
import com.example.finalproj.ui.theme.Eat2FitTheme

@Composable
fun HeaderLogo() {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            8.dp,
            alignment = Alignment.Bottom
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 120.dp)
            .background(color = Eat2FitTheme.colors.emptyGreen)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.logo)
                .crossfade(true)
                .error(R.drawable.expired)
                .build(),
            contentDescription = "Eat2Fit Logo",
            modifier = Modifier.size(100.dp)
        )
    }
}