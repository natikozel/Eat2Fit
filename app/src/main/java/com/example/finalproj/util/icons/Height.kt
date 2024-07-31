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
fun rememberHeight(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "height",
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
                moveTo(20f, 34.25f)
                quadToRelative(-0.25f, 0f, -0.479f, -0.083f)
                quadToRelative(-0.229f, -0.084f, -0.438f, -0.292f)
                lineToRelative(-4.708f, -4.708f)
                quadToRelative(-0.375f, -0.375f, -0.354f, -0.917f)
                quadToRelative(0.021f, -0.542f, 0.437f, -0.958f)
                quadToRelative(0.375f, -0.375f, 0.938f, -0.375f)
                quadToRelative(0.562f, 0f, 0.979f, 0.416f)
                lineToRelative(2.333f, 2.292f)
                verticalLineToRelative(-19.25f)
                lineToRelative(-2.375f, 2.333f)
                quadToRelative(-0.416f, 0.417f, -0.958f, 0.396f)
                quadToRelative(-0.542f, -0.021f, -0.917f, -0.396f)
                quadToRelative(-0.416f, -0.416f, -0.416f, -0.958f)
                reflectiveQuadToRelative(0.375f, -0.958f)
                lineToRelative(4.666f, -4.667f)
                quadToRelative(0.209f, -0.208f, 0.438f, -0.292f)
                quadToRelative(0.229f, -0.083f, 0.479f, -0.083f)
                quadToRelative(0.25f, 0f, 0.479f, 0.083f)
                quadToRelative(0.229f, 0.084f, 0.438f, 0.292f)
                lineToRelative(4.708f, 4.708f)
                quadToRelative(0.375f, 0.375f, 0.354f, 0.917f)
                quadToRelative(-0.021f, 0.542f, -0.396f, 0.958f)
                quadToRelative(-0.416f, 0.375f, -0.979f, 0.375f)
                quadToRelative(-0.562f, 0f, -0.979f, -0.416f)
                lineToRelative(-2.292f, -2.292f)
                verticalLineToRelative(19.25f)
                lineToRelative(2.334f, -2.333f)
                quadToRelative(0.416f, -0.417f, 0.958f, -0.396f)
                quadToRelative(0.542f, 0.021f, 0.958f, 0.396f)
                quadToRelative(0.375f, 0.416f, 0.375f, 0.958f)
                reflectiveQuadToRelative(-0.375f, 0.958f)
                lineToRelative(-4.666f, 4.667f)
                quadToRelative(-0.209f, 0.208f, -0.438f, 0.292f)
                quadToRelative(-0.229f, 0.083f, -0.479f, 0.083f)
                close()
            }
        }.build()
    }
}