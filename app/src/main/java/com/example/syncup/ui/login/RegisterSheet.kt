package com.example.syncup.ui.login

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterSheet(
    onDismiss: () -> Unit,
    onRegister: (username: String, email: String, password: String) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") })
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") })
        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") })
        if (password != confirmPassword) {
            Text("Passwords do not match")
        }
        Button(
            onClick = {
                onRegister(username, email, password)
                username = ""
                email = ""
                password = ""
                confirmPassword = ""
            },
            enabled = password == confirmPassword && password.isNotBlank() && email.isNotBlank() && username.isNotBlank()
        ) {
            Text("Register")
        }
    }
}