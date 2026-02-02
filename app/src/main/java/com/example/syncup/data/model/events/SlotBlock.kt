package com.example.syncup.data.model.events

data class SlotBlock(
    val slot: TimeSlot,
    val reason: BlockReason
)

enum class BlockReason {
    ALREADY_SUGGESTED,
    CREATOR_UNAVAILABLE,
    CONFLICTS_WITH_OTHER_EVENT
}
