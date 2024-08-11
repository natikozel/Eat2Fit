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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.finalproj.models.Goal
import com.example.finalproj.ui.theme.Eat2FitTheme
import com.example.finalproj.util.roundToHalf

@Composable
fun CircularDeterminateIndicator(
    progress : Float, currentCalories : String, availableCalories : String, modifier : Modifier = Modifier, userGoal : Goal
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress/100 ,
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

        val color =
            if (progress >= 90 && userGoal != Goal.GAIN) {
                Eat2FitTheme.colors.red
            } else {
                Eat2FitTheme.colors.lightGreen
            }


        Column(verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "${roundToHalf(progress)}%",
                color = color,
                textAlign = TextAlign.Center ,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "$currentCalories\nCalories" ,
                textAlign = TextAlign.Center ,
                style = MaterialTheme.typography.titleLarge
            )
        }

    }
}