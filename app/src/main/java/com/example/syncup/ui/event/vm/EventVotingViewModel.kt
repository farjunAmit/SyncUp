package com.example.syncup.ui.event.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.syncup.data.repository.event.EventRepository
import com.example.syncup.ui.event.uistate.EventVotingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EventVotingViewModel (private val eventRepo: EventRepository): ViewModel() {
    private val _uiState = MutableStateFlow(EventVotingUiState())
    val uiState: StateFlow<EventVotingUiState> = _uiState.asStateFlow()

    fun loadEvent(eventId: String){
        viewModelScope.launch {
            _uiState.update { current ->
                current.copy(event = eventRepo.getById(eventId))
            }
        }
    }
}