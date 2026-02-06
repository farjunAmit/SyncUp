package com.example.syncup.ui.group.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.syncup.data.model.events.Event
import com.example.syncup.data.model.events.EventStatus
import com.example.syncup.data.model.events.TimeSlot
import com.example.syncup.data.model.groups.Group
import com.example.syncup.data.repository.event.EventRepository
import com.example.syncup.data.repository.group.GroupsRepository
import com.example.syncup.ui.group.uistate.GroupDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

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
@HiltViewModel
class GroupDetailViewModel @Inject constructor(
    private val groupRepo: GroupsRepository,
    private val eventRepo: EventRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(GroupDetailUiState())
    val uiState: StateFlow<GroupDetailUiState> = _uiState.asStateFlow()
    val groups: StateFlow<List<Group>> =
        groupRepo.observeAll()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    /**
     * Loads the details of a specific group from the repository and updates the UI state.
     */
    fun loadGroup(id: Long) {
        val group = groups.value.find { it.id == id }
        _uiState.update { current ->
            current.copy(group = group)
        }
    }

    /**
     * Loads the events of a specific group from the repository and updates the UI state.
     */
    fun loadEvents(groupId: Long) {
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
    fun deleteEvent(eventId: Long) {
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
            if(finalDate != null && event.eventStatus == EventStatus.DECIDED){
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