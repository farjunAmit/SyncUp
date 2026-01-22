package com.example.syncup.ui.event.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.syncup.data.model.events.EventType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventTypesDropDown(
    eventTypes: List<EventType>,
    currentEventType: EventType?,
    onItemClick: (eventType: EventType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val selectedText = currentEventType?.name ?: "Choose type"
    val selectedColor: Color? =
        currentEventType?.let { Color(it.color) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        // Anchor (what you see when menu is closed)
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            label = { Text("Type") },
            leadingIcon = {
                if (selectedColor != null) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(
                                color = selectedColor,
                                shape = CircleShape
                            )
                    )
                }
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            eventTypes.forEach { eventType ->
                DropdownMenuItem(
                    text = { EventTypeOption(eventType = eventType) },
                    onClick = {
                        onItemClick(eventType)
                        expanded = false
                    }
                )
            }
        }
    }
}
