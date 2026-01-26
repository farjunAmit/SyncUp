package com.example.syncup.ui.group.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * GroupCreateSheet
 *
 * Bottom sheet used for creating a new group.
 * Allows the user to:
 * - Enter a group name
 * - Add one or more email invitations
 * - Confirm or cancel the creation process
 *
 * All state in this component is local UI state.
 * Business logic is delegated to the caller via callbacks.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun GroupCreateSheet(
    onCreate: (String, List<String>) -> Unit,
    onCancel: () -> Unit,
) {

    ModalBottomSheet(
        onDismissRequest = { onCancel() },
    ) {

        // Local UI state for form inputs
        var groupName by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        val invitedEmails = remember { mutableStateListOf<String>() }

        /**
         * Adds the current email input to the invitations list.
         * Trims whitespace, normalizes casing, and prevents duplicates.
         */
        fun addEmail() {
            val e = email.trim().lowercase()
            if (e !in invitedEmails) {
                invitedEmails.add(e)
                email = ""
            }
        }

        Column(modifier = Modifier.Companion.padding(16.dp)) {

            Text("Create group")

            Spacer(Modifier.Companion.height(12.dp))

            TextField(
                value = groupName,
                onValueChange = { groupName = it },
                label = { Text("Group name") },
                singleLine = true,
            )

            Spacer(Modifier.Companion.height(12.dp))

            Row(
                modifier = Modifier.Companion.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    singleLine = true,
                    modifier = Modifier.Companion.weight(1f)
                )

                Button(
                    onClick = { addEmail() },
                    enabled = email.isNotBlank()
                ) {
                    Text("Add")
                }
            }

            Spacer(Modifier.Companion.height(12.dp))

            // Displays added email invitations as removable chips
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                invitedEmails.forEach {
                    AssistChip(
                        onClick = {},
                        label = { Text(it) },
                        trailingIcon = {
                            IconButton(onClick = { invitedEmails.remove(it) }) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "Remove"
                                )
                            }
                        }
                    )
                }
            }

            Spacer(Modifier.Companion.height(12.dp))

            Row {
                Button(
                    onClick = {
                        onCreate(groupName, invitedEmails.toList())
                        groupName = ""
                        email = ""
                        invitedEmails.clear()
                    },
                    enabled = groupName.isNotBlank()
                ) {
                    Text("Create")
                }

                Spacer(Modifier.Companion.width(12.dp))

                TextButton(
                    onClick = {
                        onCancel()
                        groupName = ""
                        email = ""
                        invitedEmails.clear()
                    }
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}