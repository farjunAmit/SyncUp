package com.example.syncup.ui.event.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.alpha
import androidx.core.graphics.toColorInt
import com.example.syncup.data.model.events.EventType

@Composable
fun EventTypeOption(eventType: EventType){
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ){
        Box(
            modifier = Modifier.size(10.dp)
                .background(
                    color = Color(eventType.color),
                    shape = CircleShape
                )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(eventType.name)
    }
}