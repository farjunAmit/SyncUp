package com.example.syncup.ui.group

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.syncup.ui.group.components.GroupItem.GroupCreateSheet
import com.example.syncup.ui.group.components.GroupItem.GroupGrid


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsScreen(viewModel: GroupsViewModel, modifier: Modifier = Modifier) {
    val state = viewModel.uiState.collectAsState().value
    var showCreateSheet by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(16.dp))
    {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Groups") })
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { showCreateSheet = true })
                {
                    Icon(Icons.Filled.Add, contentDescription = "Add group")
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
            ) {
                Spacer(Modifier.height(12.dp))
                Text("Group: ${state.groups.size}")
                GroupGrid(
                    state.groups,
                    { groupId -> { /*Todo: here will be group click - navigate to group screen */ } },
                    { groupId -> viewModel.deleteGroup(groupId) },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
            }
        }
        if (showCreateSheet) {
            GroupCreateSheet(
                onCreate = { name, invitedEmails ->
                    viewModel.addGroup(name, invitedEmails)
                    showCreateSheet = false
                },
                onCancel = { showCreateSheet = false }
            )

        }
    }
}