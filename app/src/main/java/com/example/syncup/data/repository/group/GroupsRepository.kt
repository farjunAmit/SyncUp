package com.example.syncup.data.repository.group

import com.example.syncup.data.model.Group

interface GroupsRepository {
    fun getAll(): List<Group>
    suspend fun create(name: String, invitedEmails: List<String> = emptyList()): Group
    suspend fun rename(id: String, newName: String)
    suspend fun delete(id: String)
}