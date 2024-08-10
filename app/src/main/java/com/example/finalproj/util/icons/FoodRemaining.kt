package com.example.finalproj.util.icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Composable
fun rememberRemainingFood(): ImageVector {
    return ImageVector.Builder(
        name = "no_food",
        defaultWidth = 40.0.dp,
        defaultHeight = 40.0.dp,
        viewportWidth = 40.0f,
        viewportHeight = 40.0f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            fillAlpha = 1f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1.0f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Miter,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(33.5f, 37.25f)
            lineTo(2.375f, 6.125f)
            quadToRelative(-0.417f, -0.417f, -0.396f, -0.937f)
            quadToRelative(0.021f, -0.521f, 0.396f, -0.938f)
            quadToRelative(0.417f, -0.375f, 0.958f, -0.375f)
            quadToRelative(0.542f, 0f, 0.959f, 0.375f)
            lineToRelative(31.125f, 31.125f)
            quadToRelative(0.375f, 0.417f, 0.375f, 0.937f)
            quadToRelative(0f, 0.521f, -0.375f, 0.938f)
            quadToRelative(-0.417f, 0.417f, -0.959f, 0.417f)
            quadToRelative(-0.541f, 0f, -0.958f, -0.417f)
            close()
            moveToRelative(2.167f, -5.375f)
            lineToRelative(-2.375f, -2.417f)
            lineTo(35f, 11.792f)
            horizontalLineTo(18.875f)
            lineToRelative(-0.167f, -1.209f)
            quadToRelative(-0.041f, -0.583f, 0.334f, -1f)
            quadToRelative(0.375f, -0.416f, 1f, -0.416f)
            horizontalLineToRelative(6.833f)
            verticalLineTo(3.292f)
            quadToRelative(0f, -0.5f, 0.396f, -0.896f)
            reflectiveQuadTo(28.208f, 2f)
            quadToRelative(0.542f, 0f, 0.938f, 0.396f)
            quadToRelative(0.396f, 0.396f, 0.396f, 0.896f)
            verticalLineToRelative(5.875f)
            horizontalLineToRelative(7f)
            quadToRelative(0.583f, 0f, 1f, 0.416f)
            quadToRelative(0.416f, 0.417f, 0.333f, 1f)
            close()
            moveTo(26.292f, 22.5f)
            close()
            moveTo(3.333f, 32.25f)
            quadToRelative(-0.583f, 0f, -0.958f, -0.375f)
            reflectiveQuadTo(2f, 30.958f)
            quadToRelative(0f, -0.583f, 0.375f, -0.958f)
            reflectiveQuadToRelative(0.958f, -0.375f)
            horizontalLineToRelative(22.125f)
            quadToRelative(0.542f, 0f, 0.938f, 0.375f)
            quadToRelative(0.396f, 0.375f, 0.396f, 0.958f)
            quadToRelative(0f, 0.542f, -0.396f, 0.917f)
            reflectiveQuadToRelative(-0.938f, 0.375f)
            close()
            moveToRelative(0f, 5.792f)
            quadToRelative(-0.583f, 0f, -0.958f, -0.375f)
            reflectiveQuadTo(2f, 36.75f)
            quadToRelative(0f, -0.583f, 0.375f, -0.958f)
            reflectiveQuadToRelative(0.958f, -0.375f)
            horizontalLineToRelative(22.125f)
            quadToRelative(0.542f, 0f, 0.938f, 0.395f)
            quadToRelative(0.396f, 0.396f, 0.396f, 0.938f)
            quadToRelative(0f, 0.542f, -0.396f, 0.917f)
            reflectiveQuadToRelative(-0.938f, 0.375f)
            close()
            moveToRelative(14.209f, -20.5f)
            verticalLineToRelative(2.625f)
            quadToRelative(-0.75f, -0.125f, -1.521f, -0.188f)
            quadToRelative(-0.771f, -0.062f, -1.646f, -0.062f)
            quadToRelative(-3.625f, 0f, -5.896f, 1.125f)
            reflectiveQuadToRelative(-3.187f, 3.041f)
            horizontalLineToRelative(18.791f)
            lineToRelative(2.667f, 2.625f)
            horizontalLineTo(3.333f)
            quadToRelative(-0.583f, 0f, -0.958f, -0.396f)
            quadToRelative(-0.375f, -0.395f, -0.292f, -0.895f)
            quadToRelative(0.542f, -3.667f, 3.688f, -5.896f)
            reflectiveQuadToRelative(8.604f, -2.229f)
            quadToRelative(0.875f, 0f, 1.646f, 0.062f)
            quadToRelative(0.771f, 0.063f, 1.521f, 0.188f)
            close()
            moveToRelative(-3.167f, 6.541f)
            close()
        }
    }.build()

}