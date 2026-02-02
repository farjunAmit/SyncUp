package com.example.syncup.ui.event.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.syncup.data.model.events.DecisionMode
import com.example.syncup.data.model.events.TimeSlot
import com.example.syncup.data.repository.event.EventRepository
import com.example.syncup.ui.event.uistate.CreateEventUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * CreateEventViewModel
 *
 * ViewModel responsible for managing the UI state of CreateEventScreen.
 *
 */
@HiltViewModel
class CreateEventViewModel @Inject constructor(
    val eventRepo: EventRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateEventUiState())
    val uiState: StateFlow<CreateEventUiState> = _uiState.asStateFlow()

    fun loadEventTypes(groupId: Long) {
        viewModelScope.launch {
            val eventTypes = eventRepo.getEventTypesForGroup(groupId).map { it.value }

            _uiState.update { current ->
                current.copy(
                    eventTypes = eventTypes,
                    selectedEventType = current.selectedEventType ?: eventTypes.firstOrNull()
                )
            }
        }
    }

    fun loadEvent(eventId: Long) {
        viewModelScope.launch {
            val event = eventRepo.getById(eventId)
            _uiState.update { current ->
                current.copy(
                    event = event
                )
            }
        }
    }

    fun addEventType(groupId: Long, type: String, color: Long) {
        viewModelScope.launch {
            val newType = eventRepo.addEventType(groupId, type, color)
            val eventTypes = eventRepo.getEventTypesForGroup(groupId).map { it.value }
            _uiState.update { current ->
                current.copy(
                    eventTypes = eventTypes,
                    selectedEventType = newType
                )
            }
        }
    }

    fun setEventType(eventTypeId: Long) {
        _uiState.update { current ->
            val eventType = current.eventTypes.find { it.id == eventTypeId }
            current.copy(
                selectedEventType = eventType
            )
        }
    }

    /**
     * Creates a new event and optionally invites emails.
     */
    fun createEvent(
        groupId: Long,
        title: String,
        possibleSlots: Set<TimeSlot>,
        description: String,
        decisionMode: DecisionMode,
        eventTypeId: Long?
    ) {
        viewModelScope.launch {
            eventRepo.create(groupId, title, possibleSlots, description, decisionMode, eventTypeId)
        }
    }
}