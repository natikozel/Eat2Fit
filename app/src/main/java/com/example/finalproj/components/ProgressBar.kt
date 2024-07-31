package com.example.finalproj.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CircularDeterminateIndicator(
    progress : Float , storageUsed : String , storageTotal : String , modifier : Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress ,
        animationSpec = tween(durationMillis = 1000 , easing = LinearOutSlowInEasing) ,
        label = ""
    )

    Box(
        contentAlignment = Alignment.Center , modifier = modifier.size(180.dp)
    ) {
        CircularProgressIndicator(
            progress = { 1f } ,
            modifier = Modifier.fillMaxSize() ,
            color = MaterialTheme.colorScheme.primaryContainer ,
            strokeWidth = 6.dp ,
        )
        CircularProgressIndicator(
            progress = { animatedProgress } ,
            modifier = Modifier
                .animateContentSize()
                .fillMaxSize() ,
            color = MaterialTheme.colorScheme.primary ,
            strokeWidth = 6.dp ,
            strokeCap = StrokeCap.Round ,
        )

        Column(verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "87%" ,
                textAlign = TextAlign.Center ,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "1305 Calories" ,
                textAlign = TextAlign.Center ,
                style = MaterialTheme.typography.titleLarge
            )
        }

    }
}