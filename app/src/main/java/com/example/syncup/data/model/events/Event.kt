package com.example.syncup.data.model.events

import java.util.Date

class Event(
    val id: String,
    val groupId: String,
    title: String,
    possibleDates: List<Date>,
    description: String
) {
    var title: String = title
        private set
    var possibleDates: List<Date> = possibleDates
        private set
    var description: String = description
        private set
    var eventStatus = EventStatus.VOTING
        private set
    var finalDate: Date? = null
        private set

    private val _userVotes: MutableMap<String, MutableMap<Date, Vote>> =
        mutableMapOf()
    val userVotes: Map<String, Map<Date, Vote>>
        get() = _userVotes
}

enum class EventStatus {
    VOTING,
    DECIDED,
    CANCELLED
}