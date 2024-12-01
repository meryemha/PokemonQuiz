package com.example.myapplication.utils

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

enum class Orientation {
    Portrait, Landscape
}

@Composable
fun rememberOrientation(): Orientation {
    val configuration = LocalConfiguration.current
    return if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        Orientation.Landscape
    } else {
        Orientation.Portrait
    }
}
