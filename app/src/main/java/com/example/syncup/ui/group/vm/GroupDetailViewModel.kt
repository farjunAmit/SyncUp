package com.example.syncup.ui.group.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.syncup.data.model.events.Event
import com.example.syncup.data.model.events.TimeSlot
import com.example.syncup.data.repository.event.EventRepository
import com.example.syncup.data.repository.group.GroupsRepository
import com.example.syncup.ui.group.uistate.GroupDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * GroupDetailViewModel
 *
 * ViewModel responsible for managing the UI state of GroupDetailScreen.
 * Holds the details of a specific group and exposes it as a StateFlow to the UI.
 *
 * Data operations are delegated to GroupsRepository.
 *
 * Note: Current implementation uses a simple "reload after change" strategy,
 * which is enough for the in-memory repository and MVP stage.
 */
class GroupDetailViewModel(
    private val groupRepo: GroupsRepository,
    private val eventRepo: EventRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(GroupDetailUiState())
    val uiState: StateFlow<GroupDetailUiState> = _uiState.asStateFlow()

    /**
     * Loads the details of a specific group from the repository and updates the UI state.
     * Runs in viewModelScope to keep async work lifecycle-aware.
     */
    init {
        loadGroups()
    }

    /**
     * Loads the current list of groups from the repository and updates the UI state.
     */
    private fun loadGroups() {
        viewModelScope.launch {
            _uiState.update { current ->
                current.copy(groups = groupRepo.getAll())
            }
        }
    }

    /**
     * Loads the details of a specific group from the repository and updates the UI state.
     */
    fun loadGroup(id: String) {
        viewModelScope.launch {
            // If the UI state is empty, load groups before loading the specific group
            if (_uiState.value.groups.isEmpty()) {
                val groups = groupRepo.getAll()
                _uiState.update { it.copy(groups = groups) }
            }
            val group = _uiState.value.groups.find { it.id == id }
            _uiState.update { it.copy(group = group) }
        }
    }

    /**
     * Loads the events of a specific group from the repository and updates the UI state.
     */
    fun loadEvents(groupId: String) {
        viewModelScope.launch {
            val events = eventRepo.getAll(groupId)
            val eventTypes = eventRepo.getEventTypesForGroup(groupId)
            val scheduledEvents = getScheduledEvents(events)
            _uiState.update { current ->
                current.copy(events = events, scheduledEvents = scheduledEvents, eventTypes = eventTypes)
            }
        }
    }

    /**
     * Deletes a group by id, then refreshes the UI state.
     */
    fun deleteEvent(eventId: String) {
        viewModelScope.launch {
            val group = _uiState.value.group
            if (group != null) {
                eventRepo.delete(eventId)
                loadEvents(group.id)
            }
        }
    }

    private fun getScheduledEvents(eventList: List<Event>): Map<LocalDate, List<Event>>{
        val scheduledEvents = mutableMapOf<LocalDate, MutableList<Event>>()
        eventList.forEach {event ->
            val finalDate = event.finalDate
            if(finalDate != null){
                val date = finalDate.date
                if(!scheduledEvents.containsKey(date)){
                    scheduledEvents[date] = mutableListOf()
                }
                scheduledEvents[date]!!.add(event)
            }
        }
        // Convert the map to an immutable map
        val result = mutableMapOf<LocalDate, List<Event>>()
        for ((date, events) in scheduledEvents) {
            result[date] = events
        }
        return result
    }
}