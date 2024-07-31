package com.example.finalproj.util.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
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


fun rememberBarcodeScanner(): ImageVector {
    return ImageVector.Builder(
        name = "barcode_scanner",
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
            moveTo(1.917f, 5.167f)
            horizontalLineToRelative(7.666f)
            verticalLineToRelative(2.625f)
            horizontalLineTo(4.542f)
            verticalLineToRelative(5.041f)
            horizontalLineTo(1.917f)
            close()
            moveToRelative(28.5f, 0f)
            horizontalLineToRelative(7.666f)
            verticalLineToRelative(7.666f)
            horizontalLineToRelative(-2.625f)
            verticalLineTo(7.792f)
            horizontalLineToRelative(-5.041f)
            close()
            moveToRelative(5.041f, 26.958f)
            verticalLineToRelative(-5f)
            horizontalLineToRelative(2.625f)
            verticalLineToRelative(7.667f)
            horizontalLineToRelative(-7.666f)
            verticalLineToRelative(-2.667f)
            close()
            moveToRelative(-30.916f, -5f)
            verticalLineToRelative(5f)
            horizontalLineToRelative(5.041f)
            verticalLineToRelative(2.667f)
            horizontalLineTo(1.917f)
            verticalLineToRelative(-7.667f)
            close()
            moveToRelative(7.083f, -17.333f)
            horizontalLineToRelative(1.667f)
            verticalLineToRelative(20.333f)
            horizontalLineToRelative(-1.667f)
            close()
            moveToRelative(-5f, 0f)
            horizontalLineToRelative(3.333f)
            verticalLineToRelative(20.333f)
            horizontalLineTo(6.625f)
            close()
            moveToRelative(10f, 0f)
            horizontalLineTo(20f)
            verticalLineToRelative(20.333f)
            horizontalLineToRelative(-3.375f)
            close()
            moveToRelative(11.75f, 0f)
            horizontalLineToRelative(1.708f)
            verticalLineToRelative(20.333f)
            horizontalLineToRelative(-1.708f)
            close()
            moveToRelative(3.417f, 0f)
            horizontalLineToRelative(1.583f)
            verticalLineToRelative(20.333f)
            horizontalLineToRelative(-1.583f)
            close()
            moveToRelative(-10.084f, 0f)
            horizontalLineToRelative(5f)
            verticalLineToRelative(20.333f)
            horizontalLineToRelative(-5f)
            close()
        }
    }.build()
}
