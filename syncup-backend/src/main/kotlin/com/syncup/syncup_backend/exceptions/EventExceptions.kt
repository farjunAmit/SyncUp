package com.syncup.syncup_backend.exceptions

class EventNotFoundException(id: Long) : RuntimeException("Event with id $id not found")
class EmptyPossibleSlotsException(title: String): RuntimeException("Event '$title' must have at least one possible slot")