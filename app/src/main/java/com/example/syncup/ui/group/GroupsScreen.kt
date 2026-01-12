package com.example.syncup.ui.group

import android.R.attr.text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.syncup.ui.group.components.GroupItem.GroupGrid
import com.example.syncup.ui.group.components.GroupItem.GroupItem

@Composable
fun GroupsScreen(viewModel: GroupsViewModel, modifier: Modifier = Modifier) {
    val state = viewModel.uiState.collectAsState().value
    Column(modifier = modifier.padding(16.dp))
    {
        Spacer(Modifier.height(12.dp))
        Text("Group: ${state.groups.size}")
        GroupGrid(
            state.groups,
            { groupId -> { /*Todo: here will be group click - navigate to group screen */ } },
            { groupId -> viewModel.deleteGroup(groupId) },
            modifier = Modifier.weight(1f)
        )
    }
}