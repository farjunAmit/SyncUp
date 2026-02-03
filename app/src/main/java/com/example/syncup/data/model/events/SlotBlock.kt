package com.example.syncup.data.model.events

data class SlotBlock(
    val slot: TimeSlot,
    val reason: BlockReason
)

enum class BlockReason {
    ALREADY_SUGGESTED,
    CREATOR_UNAVAILABLE,
    CONFLICTS_WITH_OTHER_EVENT;

    override fun toString(): String{
        return when (this) {
            ALREADY_SUGGESTED -> "This time slot has already been suggested before"
            CREATOR_UNAVAILABLE -> "The creator is not available for this time slot"
            CONFLICTS_WITH_OTHER_EVENT -> "This time slot conflicts with another event"
        }
    }
}
