package com.example.syncup.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.syncup.data.session.SessionStore
import kotlinx.coroutines.flow.first

@Composable
fun GateScreen(
    sessionStore: SessionStore,
    onGoHome: () -> Unit,
    onGoLogin: () -> Unit
) {
    LaunchedEffect(Unit) {
        val token = sessionStore.tokenFlow.first()
        if (!token.isNullOrBlank()) onGoHome() else onGoLogin()
    }
    Text("Checking session...")
}