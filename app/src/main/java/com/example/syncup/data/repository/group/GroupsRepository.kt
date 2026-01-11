package com.example.syncup.data.repository.group

import com.example.syncup.data.model.Group

interface GroupsRepository {
    fun getAll(): List<Group>
    fun create(name: String): Group
    fun rename(id: String, newName: String)
    fun delete(id: String)
}