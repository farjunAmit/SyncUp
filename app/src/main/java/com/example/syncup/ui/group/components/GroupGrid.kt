package com.example.syncup.ui.group.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.syncup.data.model.groups.Group

/**
 * GroupGrid
 *
 * Displays a grid of group items.
 * Responsible only for layout and delegation of user actions
 * to the parent component.
 *
 * Each group is rendered using the GroupItem composable.
 */
@Composable
fun GroupGrid(
    groups: List<Group>,
    onGroupClick: (String) -> Unit,
    onDelete: (String) -> Unit,
    onEdit: (String, String) -> Unit,
    modifier: Modifier = Modifier.Companion
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(groups) { group ->
            GroupItem(
                group,
                onGroupClick = { onGroupClick(group.id) },
                onDelete = { onDelete(group.id) },
                onEdit = { name -> onEdit(group.id, name) }
            )
        }
    }
}