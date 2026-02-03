package com.example.syncup.ui.event.utils

import androidx.compose.runtime.staticCompositionLocalOf

val LocalShowMessage = staticCompositionLocalOf<(String) -> Unit> {
    error("LocalShowMessage not provided")
}

