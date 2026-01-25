package com.syncup.syncup_backend.dto

import com.syncup.syncup_backend.model.PartOfDay
import java.time.LocalDate

data class TimeSlotDto(
    var date: LocalDate,
    var partOfDay: PartOfDay
)
