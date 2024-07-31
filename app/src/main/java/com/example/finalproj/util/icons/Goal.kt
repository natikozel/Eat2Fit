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
fun rememberAccessibility(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "accessibility",
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
                moveTo(20f, 9.667f)
                quadToRelative(-1.25f, 0f, -2.167f, -0.896f)
                quadToRelative(-0.916f, -0.896f, -0.916f, -2.188f)
                quadToRelative(0f, -1.291f, 0.895f, -2.187f)
                quadTo(18.708f, 3.5f, 20f, 3.5f)
                quadToRelative(1.25f, 0f, 2.167f, 0.896f)
                quadToRelative(0.916f, 0.896f, 0.916f, 2.187f)
                quadToRelative(0f, 1.25f, -0.895f, 2.167f)
                quadToRelative(-0.896f, 0.917f, -2.188f, 0.917f)
                close()
                moveToRelative(-3.208f, 26.791f)
                quadToRelative(-0.542f, 0f, -0.938f, -0.396f)
                quadToRelative(-0.396f, -0.395f, -0.396f, -0.937f)
                verticalLineToRelative(-20.5f)
                horizontalLineTo(6.583f)
                quadToRelative(-0.583f, 0f, -0.958f, -0.396f)
                reflectiveQuadToRelative(-0.375f, -0.937f)
                quadToRelative(0f, -0.542f, 0.375f, -0.938f)
                quadToRelative(0.375f, -0.396f, 0.958f, -0.396f)
                horizontalLineToRelative(26.875f)
                quadToRelative(0.542f, 0f, 0.917f, 0.396f)
                reflectiveQuadToRelative(0.375f, 0.938f)
                quadToRelative(0f, 0.541f, -0.375f, 0.937f)
                reflectiveQuadToRelative(-0.917f, 0.396f)
                horizontalLineToRelative(-8.916f)
                verticalLineToRelative(20.5f)
                quadToRelative(0f, 0.542f, -0.396f, 0.937f)
                quadToRelative(-0.396f, 0.396f, -0.938f, 0.396f)
                quadToRelative(-0.541f, 0f, -0.937f, -0.396f)
                quadToRelative(-0.396f, -0.395f, -0.396f, -0.937f)
                verticalLineToRelative(-9.083f)
                horizontalLineToRelative(-3.75f)
                verticalLineToRelative(9.083f)
                quadToRelative(0f, 0.542f, -0.396f, 0.937f)
                quadToRelative(-0.396f, 0.396f, -0.937f, 0.396f)
                close()
            }
        }.build()
    }
}