package com.example.syncup.ui.event.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.syncup.data.model.events.BlockReason
import com.example.syncup.data.model.events.DecisionMode
import com.example.syncup.data.model.events.Event
import com.example.syncup.data.model.events.SlotBlock
import com.example.syncup.data.model.events.TimeSlot
import com.example.syncup.data.repository.event.EventRepository
import com.example.syncup.ui.event.uistate.CreateEventUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * CreateEventViewModel
 *
 * ViewModel responsible for managing the UI state of the Create / Edit Event screen.
 *
 * Design notes:
 * - This screen is form-oriented, so we do not continuously collect Flows.
 * - The repository follows a local-first approach (Room-backed Flows).
 * - We take one-time snapshots from those Flows using `first()`.
 * - The database remains the single source of truth.
 */
@HiltViewModel
class CreateEventViewModel @Inject constructor(
    private val eventRepo: EventRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateEventUiState())
    val uiState: StateFlow<CreateEventUiState> = _uiState.asStateFlow()

    /**
     * Loads all event types for a given group.
     *
     * Implementation:
     * - Reads a one-time snapshot from the Room-backed Flow.
     * - Updates the UI state with available types.
     * - Selects the first type by default if none is selected.
     *
     * @param groupId ID of the group whose event types should be loaded.
     */
    fun loadEventTypes(groupId: Long) {
        viewModelScope.launch {
            val eventTypesMap = eventRepo.observeEventTypes(groupId).first()
            val eventTypes = eventTypesMap.values.toList()

            _uiState.update { current ->
                current.copy(
                    eventTypes = eventTypes,
                    selectedEventType = current.selectedEventType ?: eventTypes.firstOrNull()
                )
            }
        }
    }

    /**
     * Loads a single event for editing.
     *
     * Implementation:
     * - Takes a one-time snapshot from the Room-backed Flow.
     * - Prepares blocked slots for UI rendering.
     *
     * @param eventId ID of the event to load.
     */
    fun loadEvent(eventId: Long) {
        viewModelScope.launch {
            val event = eventRepo.observeById(eventId).first()
            val slotsToBlock = getBlockSlots(event)

            _uiState.update { current ->
                current.copy(
                    event = event,
                    slotsToBlock = slotsToBlock
                )
            }
        }
    }

    /**
     * Adds a new event type to the group.
     *
     * After insertion, a fresh snapshot of event types is taken from the database
     * to ensure UI consistency with the local source of truth.
     *
     * @param groupId ID of the group.
     * @param type Name of the new event type.
     * @param color Color associated with the event type.
     */
    fun addEventType(groupId: Long, type: String, color: Long) {
        viewModelScope.launch {
            val newType = eventRepo.addEventType(groupId, type, color)

            val eventTypesMap = eventRepo.observeEventTypes(groupId).first()
            val eventTypes = eventTypesMap.values.toList()

            _uiState.update { current ->
                current.copy(
                    eventTypes = eventTypes,
                    selectedEventType = newType
                )
            }
        }
    }

    /**
     * Updates the currently selected event type in the UI state.
     *
     * @param eventTypeId ID of the selected event type.
     */
    fun setEventType(eventTypeId: Long) {
        _uiState.update { current ->
            val eventType = current.eventTypes.find { it.id == eventTypeId }
            current.copy(selectedEventType = eventType)
        }
    }

    /**
     * Creates a new event.
     *
     * The repository handles database persistence and synchronization.
     * The UI layer typically navigates away after successful creation.
     *
     * @param groupId ID of the group.
     * @param title Event title.
     * @param possibleSlots Set of proposed time slots.
     * @param description Event description.
     * @param decisionMode Voting/decision mode.
     * @param eventTypeId Optional associated event type.
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
            eventRepo.create(
                groupId,
                title,
                possibleSlots,
                description,
                decisionMode,
                eventTypeId
            )
        }
    }

    /**
     * Prepares a map of blocked time slots for the UI layer.
     *
     * Used when editing an event to visually mark already suggested slots.
     */
    private fun getBlockSlots(event: Event?): Map<TimeSlot, SlotBlock> {
        val slotsToBlock = mutableMapOf<TimeSlot, SlotBlock>()

        if (event != null) {
            for (slot in event.possibleSlots) {
                slotsToBlock[slot] =
                    SlotBlock(slot, BlockReason.ALREADY_SUGGESTED)
            }
        }

        return slotsToBlock.toMap()
    }
}