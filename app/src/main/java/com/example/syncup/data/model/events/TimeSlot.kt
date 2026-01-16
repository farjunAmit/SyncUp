package com.example.syncup.data.model.events

import java.time.LocalDate

data class TimeSlot(
    val date: LocalDate,
    val partOfDay: PartOfDay
)
enum class PartOfDay {
    MORNING,
    Evening,
    ALL_DAY
}