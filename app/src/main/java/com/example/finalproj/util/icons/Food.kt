package com.example.finalproj.util.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

fun rememberFastfood(): ImageVector {
    return ImageVector.Builder(
        name = "fastfood",
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
            moveTo(3.375f, 26.708f)
            quadToRelative(-0.583f, 0f, -0.937f, -0.396f)
            quadToRelative(-0.355f, -0.395f, -0.271f, -0.895f)
            quadToRelative(0.5f, -3.667f, 3.645f, -5.896f)
            quadToRelative(3.146f, -2.229f, 8.646f, -2.229f)
            quadToRelative(5.459f, 0f, 8.604f, 2.229f)
            quadToRelative(3.146f, 2.229f, 3.688f, 5.896f)
            quadToRelative(0.083f, 0.5f, -0.292f, 0.895f)
            quadToRelative(-0.375f, 0.396f, -0.916f, 0.396f)
            close()
            moveToRelative(26.083f, 11.334f)
            verticalLineToRelative(-2.625f)
            horizontalLineToRelative(3.25f)
            lineToRelative(2.334f, -23.625f)
            horizontalLineTo(18.917f)
            lineToRelative(-0.167f, -1.209f)
            quadToRelative(-0.042f, -0.583f, 0.354f, -1f)
            quadToRelative(0.396f, -0.416f, 0.979f, -0.416f)
            horizontalLineToRelative(6.875f)
            verticalLineTo(3.292f)
            quadToRelative(0f, -0.5f, 0.375f, -0.896f)
            reflectiveQuadTo(28.25f, 2f)
            quadToRelative(0.583f, 0f, 0.958f, 0.396f)
            reflectiveQuadToRelative(0.375f, 0.896f)
            verticalLineToRelative(5.875f)
            horizontalLineToRelative(7.042f)
            quadToRelative(0.583f, 0f, 0.979f, 0.416f)
            quadToRelative(0.396f, 0.417f, 0.313f, 1f)
            lineToRelative(-2.584f, 25.042f)
            quadToRelative(-0.125f, 1.042f, -0.895f, 1.729f)
            quadToRelative(-0.771f, 0.688f, -1.896f, 0.688f)
            close()
            moveToRelative(0f, -2.625f)
            horizontalLineToRelative(3.25f)
            horizontalLineToRelative(-3.25f)
            close()
            moveToRelative(-6f, -11.334f)
            quadToRelative(-0.875f, -1.916f, -3.166f, -3.041f)
            quadToRelative(-2.292f, -1.125f, -5.917f, -1.125f)
            quadToRelative(-3.583f, 0f, -5.875f, 1.125f)
            reflectiveQuadToRelative(-3.208f, 3.041f)
            close()
            moveToRelative(-9.083f, 0f)
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
        }
    }.build()
}