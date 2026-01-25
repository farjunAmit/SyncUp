package com.syncup.syncup_backend.exceptions

class EventNotFoundException(id: Long) : RuntimeException("Event with id $id not found")