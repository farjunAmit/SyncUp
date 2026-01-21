package com.example.syncup.data.model.events

/**
 * Represents a user's availability vote for a specific [TimeSlot].
 */
enum class Vote {

    /** User is fully available for the selected time slot */
    YES,

    /** User is possibly available, but prefers not to */
    YES_BUT,

    /** User is not available for the selected time slot */
    NO
}
