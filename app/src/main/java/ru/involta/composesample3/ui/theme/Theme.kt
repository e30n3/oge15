package ru.involta.composesample3.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = ButtonBottom,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = DarkGray,
)

private val LightColorPalette = lightColors(
    primary = ButtonBottom,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = WhiteGrey,
    /* Other default colors to override

    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun ComposeSample3Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    var colors = DarkColorPalette
    if (darkTheme) {
        colors = DarkColorPalette
        systemUiController.setSystemBarsColor(DarkGray)


    } else {
        colors = LightColorPalette
        systemUiController.setSystemBarsColor(WhiteGrey)
        systemUiController.statusBarDarkContentEnabled = true

    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}