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
fun rememberTransgender(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "transgender",
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
                moveTo(20f, 25.625f)
                quadToRelative(2.708f, 0f, 4.583f, -1.875f)
                reflectiveQuadToRelative(1.875f, -4.583f)
                quadToRelative(0f, -2.709f, -1.875f, -4.584f)
                quadToRelative(-1.875f, -1.875f, -4.583f, -1.875f)
                reflectiveQuadToRelative(-4.583f, 1.875f)
                quadToRelative(-1.875f, 1.875f, -1.875f, 4.584f)
                quadToRelative(0f, 2.708f, 1.875f, 4.583f)
                reflectiveQuadTo(20f, 25.625f)
                close()
                moveToRelative(0f, 12.625f)
                quadToRelative(-0.542f, 0f, -0.917f, -0.375f)
                reflectiveQuadToRelative(-0.375f, -0.917f)
                verticalLineToRelative(-2.041f)
                horizontalLineToRelative(-2.041f)
                quadToRelative(-0.542f, 0f, -0.917f, -0.375f)
                reflectiveQuadToRelative(-0.375f, -0.917f)
                quadToRelative(0f, -0.583f, 0.375f, -0.958f)
                reflectiveQuadToRelative(0.917f, -0.375f)
                horizontalLineToRelative(2.041f)
                verticalLineToRelative(-4.084f)
                quadToRelative(-3.416f, -0.583f, -5.604f, -3.125f)
                quadToRelative(-2.187f, -2.541f, -2.187f, -5.916f)
                quadToRelative(0f, -1.459f, 0.437f, -2.855f)
                quadToRelative(0.438f, -1.395f, 1.313f, -2.604f)
                lineToRelative(-1.625f, -1.583f)
                lineToRelative(-1.417f, 1.417f)
                quadToRelative(-0.375f, 0.375f, -0.896f, 0.375f)
                reflectiveQuadToRelative(-0.937f, -0.417f)
                quadToRelative(-0.375f, -0.375f, -0.375f, -0.917f)
                quadToRelative(0f, -0.541f, 0.375f, -0.916f)
                lineToRelative(1.416f, -1.417f)
                lineToRelative(-4f, -4f)
                verticalLineToRelative(3.458f)
                quadToRelative(0f, 0.584f, -0.375f, 0.959f)
                reflectiveQuadToRelative(-0.958f, 0.375f)
                quadToRelative(-0.542f, 0f, -0.917f, -0.375f)
                reflectiveQuadToRelative(-0.375f, -0.959f)
                verticalLineTo(3.042f)
                quadToRelative(0f, -0.542f, 0.375f, -0.938f)
                quadToRelative(0.375f, -0.396f, 0.917f, -0.396f)
                horizontalLineToRelative(6.667f)
                quadToRelative(0.583f, 0f, 0.958f, 0.396f)
                reflectiveQuadToRelative(0.375f, 0.938f)
                quadToRelative(0f, 0.583f, -0.375f, 0.958f)
                reflectiveQuadToRelative(-0.958f, 0.375f)
                horizontalLineTo(7.083f)
                lineToRelative(4f, 4f)
                lineToRelative(1.459f, -1.458f)
                quadToRelative(0.375f, -0.375f, 0.896f, -0.375f)
                quadToRelative(0.52f, 0f, 0.937f, 0.416f)
                quadToRelative(0.375f, 0.375f, 0.375f, 0.917f)
                reflectiveQuadToRelative(-0.375f, 0.917f)
                lineToRelative(-1.417f, 1.416f)
                lineToRelative(1.625f, 1.667f)
                quadToRelative(1.209f, -0.875f, 2.625f, -1.354f)
                quadToRelative(1.417f, -0.479f, 2.792f, -0.479f)
                quadToRelative(1.375f, 0f, 2.812f, 0.458f)
                quadToRelative(1.438f, 0.458f, 2.646f, 1.333f)
                lineToRelative(7.459f, -7.458f)
                horizontalLineToRelative(-3.459f)
                quadToRelative(-0.583f, 0f, -0.958f, -0.375f)
                reflectiveQuadToRelative(-0.375f, -0.958f)
                quadToRelative(0f, -0.542f, 0.375f, -0.938f)
                quadToRelative(0.375f, -0.396f, 0.958f, -0.396f)
                horizontalLineToRelative(6.667f)
                quadToRelative(0.542f, 0f, 0.937f, 0.396f)
                quadToRelative(0.396f, 0.396f, 0.396f, 0.938f)
                verticalLineToRelative(6.666f)
                quadToRelative(0f, 0.584f, -0.396f, 0.959f)
                quadToRelative(-0.395f, 0.375f, -0.937f, 0.375f)
                quadToRelative(-0.583f, 0f, -0.958f, -0.375f)
                reflectiveQuadToRelative(-0.375f, -0.959f)
                verticalLineTo(6.25f)
                lineToRelative(-7.459f, 7.5f)
                quadToRelative(0.875f, 1.208f, 1.334f, 2.583f)
                quadToRelative(0.458f, 1.375f, 0.458f, 2.834f)
                quadToRelative(0f, 3.375f, -2.208f, 5.916f)
                quadToRelative(-2.209f, 2.542f, -5.584f, 3.125f)
                verticalLineToRelative(4.084f)
                horizontalLineToRelative(2f)
                quadToRelative(0.542f, 0f, 0.938f, 0.375f)
                quadToRelative(0.396f, 0.375f, 0.396f, 0.958f)
                quadToRelative(0f, 0.542f, -0.396f, 0.917f)
                reflectiveQuadToRelative(-0.938f, 0.375f)
                horizontalLineToRelative(-2f)
                verticalLineToRelative(2.041f)
                quadToRelative(0f, 0.542f, -0.395f, 0.917f)
                quadToRelative(-0.396f, 0.375f, -0.938f, 0.375f)
                close()
            }
        }.build()
    }
}