package com.example.syncup.data.dto

import com.example.syncup.data.model.events.PartOfDay
import java.time.LocalDate

data class TimeSlotDto(
    var date: LocalDate,
    var partOfDay: PartOfDay
)
