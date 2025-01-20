package com.supplieswind.kmpmovies

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // para que nuestra aplicacion pueda pintar hasta los bordes de la pantalla
        enableEdgeToEdge()

        setContent {
            EnableTransparentStatusBar()
            App()
        }
    }
}

// habilitamos la fuente del status bar con un color contrario al tema actual
@Composable
private fun EnableTransparentStatusBar() {
    val view = LocalView.current
    val darkMode = isSystemInDarkTheme()

    if (!view.isInEditMode) {
        val window = (view.context as Activity).window
        window.statusBarColor = Color.Transparent.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkMode
    }
}