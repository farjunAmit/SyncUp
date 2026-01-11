package com.example.syncup.data.repository.group

import com.example.syncup.data.model.Group

class InMemoryGroupsRepository: GroupsRepository{
    private val groups = mutableListOf<Group>()
    init{
        groups.add(Group("1", "Group 1"))
        groups.add(Group("2", "Group 2"))
        groups.add(Group("3", "Group 3"))
    }

    override fun getAll(): List<Group> {
        return groups.toList()
    }

    override fun create(name: String): Group {
        //Todo: generate id automatically in a better way than this
        val newGroup = Group(groups.size.toString(), name)
        groups.add(newGroup)
        return newGroup
    }

    override fun rename(id: String, newName: String) {
        val group = groups.find { it.id == id }
        //Todo: check if group exists - if not throw exception
        group?.rename(newName)
    }

    override fun delete(id: String) {
        groups.removeIf { it.id == id }
    }
}
