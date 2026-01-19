package com.example.syncup.data.model.events

import java.time.LocalDate

/**
 * Represents a specific time option within a single calendar day.
 *
 * A [TimeSlot] is defined by:
 * - a calendar [date]
 * - a [PartOfDay] describing which portion of the day is relevant
 *
 * TimeSlot is used as:
 * - a possible option when creating an event
 * - a voting target for participants
 * - the final selected date once an event is decided
 *
 * Note:
 * This model is intentionally simple and does not include exact hours.
 * It is designed to be easily extendable to time ranges in the future
 * without changing the overall event flow.
 */
data class TimeSlot(
    val date: LocalDate,
    val partOfDay: PartOfDay
)
/**
 * Describes which part of a day a [TimeSlot] represents.
 */
enum class PartOfDay {

    /** Morning or early daytime hours */
    MORNING,

    /** Evening or late daytime hours */
    EVENING,

    /**
     * Represents availability for the entire day.
     * Semantically equivalent to both MORNING and EVENING.
     */
    ALL_DAY
}
