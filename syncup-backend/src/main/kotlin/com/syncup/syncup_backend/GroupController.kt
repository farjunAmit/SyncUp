package com.syncup.syncup_backend

import com.syncup.syncup_backend.dto.GroupDto
import com.syncup.syncup_backend.services.GroupService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/groups")
class GroupController (
    private val groupService: GroupService
){
    @GetMapping("/{id}")
    fun getGroups(@PathVariable ("id") id: Long): List<GroupDto>{
        return groupService.getGroups(id)
    }
}