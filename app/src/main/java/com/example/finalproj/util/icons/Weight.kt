package com.example.finalproj.util.icons

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
@Composable
fun rememberWeight(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "weight",
            defaultWidth = 30.0.dp,
            defaultHeight = 30.0.dp,
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
                moveTo(9.292f, 32.292f)
                horizontalLineToRelative(21.416f)
                lineToRelative(-2.541f, -17.917f)
                horizontalLineTo(11.833f)
                lineTo(9.292f, 32.292f)
                close()
                moveTo(20f, 11.708f)
                quadToRelative(0.958f, 0f, 1.625f, -0.645f)
                quadToRelative(0.667f, -0.646f, 0.667f, -1.605f)
                quadToRelative(0f, -1f, -0.667f, -1.666f)
                quadToRelative(-0.667f, -0.667f, -1.625f, -0.667f)
                reflectiveQuadToRelative(-1.625f, 0.667f)
                quadToRelative(-0.667f, 0.666f, -0.667f, 1.666f)
                quadToRelative(0f, 0.959f, 0.667f, 1.605f)
                quadToRelative(0.667f, 0.645f, 1.625f, 0.645f)
                close()
                moveToRelative(4.375f, 0f)
                horizontalLineToRelative(3.792f)
                quadToRelative(1f, 0f, 1.729f, 0.646f)
                quadToRelative(0.729f, 0.646f, 0.896f, 1.646f)
                lineToRelative(2.541f, 17.917f)
                quadToRelative(0.167f, 1.208f, -0.604f, 2.104f)
                quadToRelative(-0.771f, 0.896f, -2.021f, 0.896f)
                horizontalLineTo(9.292f)
                quadToRelative(-1.25f, 0f, -2.021f, -0.896f)
                quadToRelative(-0.771f, -0.896f, -0.604f, -2.104f)
                lineTo(9.208f, 14f)
                quadToRelative(0.167f, -1f, 0.896f, -1.646f)
                quadToRelative(0.729f, -0.646f, 1.729f, -0.646f)
                horizontalLineToRelative(3.834f)
                quadToRelative(-0.292f, -0.5f, -0.438f, -1.062f)
                quadToRelative(-0.146f, -0.563f, -0.146f, -1.188f)
                quadToRelative(0f, -2.083f, 1.438f, -3.52f)
                quadTo(17.958f, 4.5f, 20f, 4.5f)
                reflectiveQuadToRelative(3.5f, 1.438f)
                quadToRelative(1.458f, 1.437f, 1.458f, 3.52f)
                quadToRelative(0f, 0.625f, -0.166f, 1.188f)
                quadToRelative(-0.167f, 0.562f, -0.417f, 1.062f)
                close()
                moveTo(9.292f, 32.292f)
                horizontalLineToRelative(21.416f)
                close()
            }
        }.build()
    }
}