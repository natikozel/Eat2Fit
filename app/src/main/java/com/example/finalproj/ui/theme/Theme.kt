package com.example.finalproj.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.ColorScheme

import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


object Eat2FitTheme {
    val colors: Eat2FitColors
        @Composable
        get() = LocalEat2FitColors.current
}

/**
 * Eat2Fit custom Color Palette
 */
@Immutable
data class Eat2FitColors(
    val navigationBar: Color,
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
    val lightestGray: Color,
    val gradient6_1: List<Color>,
    val gradient6_2: List<Color>,
    val gradient3_1: List<Color>,
    val gradient3_2: List<Color>,
    val gradient2_1: List<Color>,
    val gradient2_2: List<Color>,
    val gradient2_3: List<Color>,
    val green: List<Color>,
    val redGrad: List<Color>,
    val orange: List<Color>,
    val disabledButton: List<Color>,
    val whiteGradiant: List<Color>,
    val lightGreen: Color,
    val white: Color,
    val brand: Color,
    val brandSecondary: Color,
    val uiBackground: Color,
    val uiBackground2: Color,
    val uiBorder: Color,
    val uiFloated: Color,
    val interactivePrimary: List<Color> = gradient2_1,
    val interactiveSecondary: List<Color> = gradient2_2,
    val interactiveMask: List<Color> = gradient6_1,
    val textPrimary: Color = brand,
    val textSecondary: Color,
    val textHelp: Color,
    val red: Color,
    val emptyGreen: Color,
    val textInteractive: Color,
    val textLink: Color,
    val tornado1: List<Color>,
    val iconPrimary: Color = brand,
    val iconSecondary: Color,
    val iconInteractive: Color,
    val iconInteractiveInactive: Color,
    val error: Color,
    val notificationBadge: Color = error,
    val isDark: Boolean
)


private val LightColorScheme = Eat2FitColors(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    lightestGray = lightestGray,
    brand = Shadow5,
    brandSecondary = Ocean3,
    uiBackground = Neutral0,
    uiBackground2 = uiBackground,
    uiBorder = Neutral4,
    navigationBar = blueWhite,
    uiFloated = FunctionalGrey,
    textSecondary = Neutral7,
    textHelp = Neutral6,
    red = red,
    textInteractive = Neutral0,
    textLink = Ocean11,
    iconSecondary = Neutral7,
    iconInteractive = LightGreen500,
    iconInteractiveInactive = Neutral7,
    error = FunctionalRed,
    redGrad = listOf(red, red2),
    disabledButton = listOf(emptyOrange, emptyOrange2),
    whiteGradiant = listOf(Neutral0, Neutral1),
    gradient6_1 = listOf(Shadow4, Ocean3, Shadow2, Ocean3, Shadow4),
    gradient6_2 = listOf(Rose4, Lavender3, Rose2, Lavender3, Rose4),
    gradient3_1 = listOf(Shadow2, Ocean3, Shadow4),
    gradient3_2 = listOf(Rose2, Lavender3, Rose4),
    gradient2_1 = listOf(Shadow4, Shadow11),
    gradient2_2 = listOf(Ocean3, Shadow3),
    gradient2_3 = listOf(Lavender3, Rose2),
    tornado1 = listOf(Shadow4, Ocean3),
    green = listOf(Green1, Green2),
    orange = listOf(Orange1, Orange2),
    lightGreen = lightGreen,
    white = Neutral0,
    emptyGreen = emptyGreen,
    isDark = false

)


private val LightColorPalette = lightColorScheme(
    primary = LightBlue800,
    onPrimary = LightBlue500,
    secondary = LightBlue900,
    background = Color.DarkGray,

    )


@Composable
fun Eat2FitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
//    val colors = if (darkTheme) DarkColorPalette else LightColorScheme
    val colors = LightColorScheme
    ProvideEat2FitColors(colors) {
        MaterialTheme(
//            colors = debugColors(darkTheme),
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

@Composable
fun ProvideEat2FitColors(
    colors: Eat2FitColors,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalEat2FitColors provides colors, content = content)
}

private val LocalEat2FitColors = staticCompositionLocalOf<Eat2FitColors> {
    error("No Eat2FitColors Palette provided")
}
