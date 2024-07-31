package com.example.finalproj.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.finalproj.ui.theme.Eat2FitTheme


@Composable
fun Eat2FitDivider(
    modifier: Modifier = Modifier,
    color: Color = Eat2FitTheme.colors.uiBorder.copy(alpha = DividerAlpha),
    thickness: Dp = 1.dp,
) {
    HorizontalDivider(
        modifier = modifier,
        color = color,
        thickness = thickness,
    )
}

private const val DividerAlpha = 0.12f

