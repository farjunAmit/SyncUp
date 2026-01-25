package com.syncup.syncup_backend.exceptions

class EventNotFoundException(id: Long) : RuntimeException("Event with id $id not found")
class EmptyPossibleSlotsException(title: String): RuntimeException("Event '$title' must have at least one possible slot")
class NotValidPossibleSlotException(eventId: Long): RuntimeException("One or more provided time slots are not valid possible slots for event with id $eventId")