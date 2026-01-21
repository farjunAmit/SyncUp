package com.example.syncup.data

import com.example.syncup.data.repository.event.InMemoryEventRepository
import com.example.syncup.data.repository.group.InMemoryGroupsRepository

/**
 * AppContainer
 *
 * Simple dependency container for the application.
 * Responsible for providing app-wide dependencies.
 *
 * This container currently exposes an in-memory implementation
 * of GroupsRepository and serves as a lightweight alternative
 * to a full dependency injection framework.
 */
class AppContainer {
    val groupsRepository = InMemoryGroupsRepository()
    val eventRepository = InMemoryEventRepository(groupsRepository)
}
