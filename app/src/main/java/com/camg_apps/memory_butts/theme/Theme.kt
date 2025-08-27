package com.camg_apps.memory_butts.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    primary = WhiteButt,
    secondary = BlackButt,
    tertiary = GrayButt
)

private val LightColorScheme = lightColorScheme(
    primary = WhiteButt,
    secondary = BlackButt,
    tertiary = GrayButt ,
    background = WhiteButt,


    /* Other default colors to override
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun MemoryButtsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme


    val view = LocalView.current

    val systemUiController = rememberSystemUiController()
    val statusBarColor = if (darkTheme) BlackButt else WhiteButt
    val statusBarTextColor = if (darkTheme) Color.White else Color.Black


    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            //set status bar color
            window.statusBarColor = statusBarColor.toArgb()

        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}