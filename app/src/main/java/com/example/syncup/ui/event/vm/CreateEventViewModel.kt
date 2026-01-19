package com.example.syncup.ui.event.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.syncup.data.model.events.TimeSlot
import com.example.syncup.data.repository.event.EventRepository
import com.example.syncup.ui.event.uistate.CreateEventUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * CreateEventViewModel
 *
 * ViewModel responsible for managing the UI state of CreateEventScreen.
 *
 */
class CreateEventViewModel(
    val eventRepo: EventRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateEventUiState())
    val uiState: StateFlow<CreateEventUiState> = _uiState.asStateFlow()

    /**
     * Creates a new event and optionally invites emails.
     */
    fun createEvent(
        groupId: String,
        title: String,
        possibleSlots: Set<TimeSlot>,
        description: String
    ) {
        viewModelScope.launch {
            eventRepo.create(groupId, title, possibleSlots, description)
        }
    }
}