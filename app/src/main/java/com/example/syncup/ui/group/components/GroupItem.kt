package com.example.syncup.ui.group.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.syncup.data.model.Group

/**
 * GroupItem
 *
 * UI component representing a single group card.
 * Displays the group name and provides actions to:
 * - Open the group
 * - Rename the group inline
 * - Delete the group
 *
 * Editing state is handled locally within the composable.
 * All data mutations are delegated to the parent via callbacks.
 */
@Composable
fun GroupItem(
    group: Group,
    onGroupClick: () -> Unit,
    onDelete: () -> Unit,
    onEdit: (name: String) -> Unit,
    modifier: Modifier = Modifier.Companion
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onGroupClick)
    ) {

        // Local UI state controlling edit mode and input value
        var isEditing by remember { mutableStateOf(false) }
        var newName by remember { mutableStateOf(group.name) }

        Column(modifier = modifier.padding(16.dp)) {

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {

                if (!isEditing) {
                    Text(text = group.name)
                } else {
                    TextField(
                        value = newName,
                        onValueChange = { newName = it },
                        modifier = Modifier.Companion.weight(1f)
                    )

                    IconButton(
                        onClick = {
                            onEdit(newName)
                            isEditing = false
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Accept new group name"
                        )
                    }
                }
            }

            Row(
                modifier = modifier.fillMaxWidth()
            ) {
                Spacer(modifier = modifier.weight(1f))

                IconButton(onClick = { isEditing = true }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit group",
                        modifier = Modifier.Companion.size(30.dp)
                    )
                }

                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        tint = MaterialTheme.colorScheme.error.copy(alpha = 0.8f),
                        contentDescription = "Delete group",
                        modifier = Modifier.Companion.size(32.dp)
                    )
                }
            }
        }
    }
}