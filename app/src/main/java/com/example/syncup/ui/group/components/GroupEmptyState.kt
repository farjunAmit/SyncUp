package com.example.syncup.ui.group.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * GroupEmptyState
 *
 * UI component displayed when the user has no groups.
 * Provides a clear empty state message and a call-to-action
 * to encourage creating the first group.
 *
 * The actual creation logic is handled by the parent via callback.
 */
@Composable
fun GroupEmptyState(onClick: () -> Unit) {

    Column(
        modifier = Modifier.Companion.fillMaxSize(),
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("No groups found")

        Spacer(Modifier.Companion.height(12.dp))

        Text("Click here to create your first group")

        Spacer(Modifier.Companion.height(12.dp))

        Button(onClick = onClick) {
            Text("Create group")
        }
    }
}