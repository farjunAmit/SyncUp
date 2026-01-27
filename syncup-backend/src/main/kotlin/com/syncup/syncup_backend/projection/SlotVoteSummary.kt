package com.syncup.syncup_backend.projection

interface SlotVoteSummary {
    fun getSlotId(): Long
    fun getTotal(): Long
    fun getYesCount(): Long
    fun getYesButCount(): Long
    fun getNoCount(): Long
}