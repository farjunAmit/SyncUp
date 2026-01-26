package com.example.syncup.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.syncup.ui.login.RegisterSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    onLoggedIn: () -> Unit
){
    val state = loginViewModel.uiState.collectAsState().value
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var registrationMode by remember { mutableStateOf(false) }
    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) onLoggedIn()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Login") },
            )
        }
    ){ innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)){
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                label = { Text("Password") }
            )
            Row {
                Button(onClick = { registrationMode = true }) {
                    Text("Register")
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = { loginViewModel.login(email, password) }) {
                    Text("Login")
                }
            }
        }
        if (registrationMode) {
            RegisterSheet(
                onDismiss = { registrationMode = false },
                onRegister = { username, email, password ->
                    loginViewModel.register(username, email, password)
                    registrationMode = false
                }
            )
        }

    }
}