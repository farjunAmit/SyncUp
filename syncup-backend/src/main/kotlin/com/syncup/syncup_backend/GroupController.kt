package com.syncup.syncup_backend

import com.syncup.syncup_backend.dto.CreateGroupRequestDto
import com.syncup.syncup_backend.dto.FetchGroupsRequestDto
import com.syncup.syncup_backend.services.GroupService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/groups")
class GroupController(
    private val groupService: GroupService
) {
    @GetMapping("/{id}")
    fun getGroups(@PathVariable("id") id: Long): List<FetchGroupsRequestDto> {
        return groupService.getGroups(id)
    }

    @PostMapping("/create/{userId}")
    fun createGroup(
        @PathVariable("userId") userId: Long,
        @RequestBody createGroupRequest: CreateGroupRequestDto
    ): FetchGroupsRequestDto {
        return groupService.createGroup(createGroupRequest,userId)
    }

    @PostMapping("/rename/{groupId}")
    fun renameGroup(
        @PathVariable("groupId") groupId: Long,
        @RequestBody name: String): FetchGroupsRequestDto{
        return groupService.renameGroup(groupId,name)
    }

    @DeleteMapping("/delete/{groupId}")
    fun deleteGroup(
        @PathVariable("groupId") groupId: Long): FetchGroupsRequestDto{
        return groupService.deleteGroup(groupId)
    }
}
