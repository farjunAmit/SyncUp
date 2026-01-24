package com.syncup.syncup_backend.services

import com.syncup.syncup_backend.dto.GroupDto
import com.syncup.syncup_backend.repositories.GroupRepository
import com.syncup.syncup_backend.toDto
import org.springframework.stereotype.Service

@Service
class GroupService (
    private val groupRepository: GroupRepository
){
    fun getGroups(id: Long): List<GroupDto>{
        return groupRepository.findUserGroups(id).map { it.toDto() }
    }
}