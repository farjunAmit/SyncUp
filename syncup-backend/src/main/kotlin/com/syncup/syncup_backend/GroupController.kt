package com.syncup.syncup_backend

import com.syncup.syncup_backend.dto.AddGroupMemberRequestDto
import com.syncup.syncup_backend.dto.CreateGroupRequestDto
import com.syncup.syncup_backend.dto.GroupSummaryDto
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
    fun getGroups(@PathVariable("id") id: Long): List<GroupSummaryDto> {
        return groupService.getGroups(id)
    }

    @GetMapping("/get/{groupId}")
    fun getGroup(@PathVariable("groupId") groupId: Long): GroupSummaryDto {
        return groupService.getGroup(groupId)
    }

    @PostMapping("/create/{userId}")
    fun createGroup(
        @PathVariable("userId") userId: Long,
        @RequestBody createGroupRequest: CreateGroupRequestDto
    ): GroupSummaryDto {
        return groupService.createGroup(createGroupRequest,userId)
    }

    @PostMapping("/rename/{groupId}")
    fun renameGroup(
        @PathVariable("groupId") groupId: Long,
        @RequestBody name: String): GroupSummaryDto{
        return groupService.renameGroup(groupId,name)
    }

    @DeleteMapping("/delete/{groupId}")
    fun deleteGroup(
        @PathVariable("groupId") groupId: Long){
        groupService.deleteGroup(groupId)
    }

    @PostMapping("/addMember/{groupId}")
    fun addMember(
        @PathVariable("groupId") groupId: Long,
        @RequestBody addGroupMemberDto: AddGroupMemberRequestDto): GroupSummaryDto{
        return groupService.addMember(groupId,addGroupMemberDto)
    }

    @GetMapping("/size/{groupId}")
    fun getGroupSize(@PathVariable("groupId") groupId: Long): Int {
        return groupService.getGroupSize(groupId)
    }

}
