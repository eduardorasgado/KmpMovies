package com.supplieswind.kmpmovies

import androidx.compose.runtime.Composable
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.supplieswind.kmpmovies.ui.screens.Navigation
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .crossfade(true)
            // que nos pinte el output en el logger
            .logger(DebugLogger())
            .build()
    }

    Navigation()
}