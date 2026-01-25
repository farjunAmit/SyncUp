package com.syncup.syncup_backend.model

import jakarta.persistence.EnumType
import jakarta.persistence.Embeddable
import jakarta.persistence.Enumerated
import java.time.LocalDate

@Embeddable
class TimeSlot(){
    var date: LocalDate = LocalDate.now()
    @Enumerated(EnumType.STRING)
    var partOfDay: PartOfDay = PartOfDay.MORNING
}

enum class PartOfDay {
    MORNING,
    EVENING
}