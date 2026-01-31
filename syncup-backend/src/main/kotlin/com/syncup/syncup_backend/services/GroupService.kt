package com.syncup.syncup_backend.services

import com.syncup.syncup_backend.dto.AddGroupMemberRequestDto
import com.syncup.syncup_backend.dto.ChangeGroupNameRequestDto
import com.syncup.syncup_backend.dto.CreateGroupRequestDto
import com.syncup.syncup_backend.dto.GroupSummaryDto
import com.syncup.syncup_backend.entity.GroupMemberEntity
import com.syncup.syncup_backend.exceptions.GroupNotFoundException
import com.syncup.syncup_backend.exceptions.UserNotFoundException
import com.syncup.syncup_backend.exceptions.UserNotFoundByEmailException
import com.syncup.syncup_backend.repositories.EventPossibleSlotRepository
import com.syncup.syncup_backend.repositories.EventRepository
import com.syncup.syncup_backend.repositories.EventTypeRepository
import com.syncup.syncup_backend.repositories.GroupMemberRepository
import com.syncup.syncup_backend.repositories.GroupRepository
import com.syncup.syncup_backend.repositories.UserRepository
import com.syncup.syncup_backend.repositories.VoteRepository
import com.syncup.syncup_backend.toGroupDto
import com.syncup.syncup_backend.toGroupEntity
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class GroupService(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository,
    private val eventRepository: EventRepository,
    private val eventTypeRepository: EventTypeRepository,
    private val voteRepository: VoteRepository,
    private val eventPossibleSlotRepository : EventPossibleSlotRepository,
    private val groupMemberRepository: GroupMemberRepository
) {
    fun getGroups(id: Long): List<GroupSummaryDto> {
        return groupRepository.findUserGroups(id).map { it.toGroupDto() }
    }

    fun getGroup(groupId: Long): GroupSummaryDto {
        val group = groupRepository.findById(groupId).orElseThrow { GroupNotFoundException(groupId) }
        return group.toGroupDto()
    }

    @Transactional
    fun createGroup(createGroupRequest: CreateGroupRequestDto, userId: Long): GroupSummaryDto {
        val group = groupRepository.save(createGroupRequest.toGroupEntity())
        val invitedEmails = createGroupRequest.invitedEmails
        val user = userRepository.findById(userId).orElseThrow { UserNotFoundException(userId) }
        groupMemberRepository.save(GroupMemberEntity(group, user))
        for (email in invitedEmails) {
            val invitedUser = userRepository.findByEmail(email)
            if (invitedUser != null) {
                groupMemberRepository.save(GroupMemberEntity(group, invitedUser))
            }
            else {
                throw UserNotFoundByEmailException(email)
            }
        }
        return group.toGroupDto()
    }

    fun renameGroup(groupId: Long, name: ChangeGroupNameRequestDto): GroupSummaryDto{
        val group = groupRepository.findById(groupId).orElseThrow { GroupNotFoundException(groupId) }
        group.name = name.name
        return groupRepository.save(group).toGroupDto()
    }

    @Transactional
    fun deleteGroup(groupId: Long) {
        val group = groupRepository.findById(groupId)
            .orElseThrow { GroupNotFoundException(groupId) }

        val eventIds = eventRepository.findAllByGroupId(groupId).map { it.id }

        if (eventIds.isNotEmpty()) {
            voteRepository.deleteAllByEvent_IdIn(eventIds)
            eventPossibleSlotRepository.deleteAllByEvent_IdIn(eventIds)
            eventRepository.deleteAllByGroupId(groupId)
        }
        eventTypeRepository.deleteAllByGroup_Id(groupId)
        groupMemberRepository.deleteAllByGroup_Id(groupId)
        groupRepository.delete(group)
    }



    fun addMember(groupId : Long, addGroupMemberDto: AddGroupMemberRequestDto): GroupSummaryDto{
        val group = groupRepository.findById(groupId).orElseThrow { GroupNotFoundException(groupId) }
        val user = userRepository.findById(addGroupMemberDto.userId).orElseThrow { UserNotFoundException(addGroupMemberDto.userId) }
        groupMemberRepository.save(GroupMemberEntity(group, user))
        return group.toGroupDto()
    }

    fun getGroupSize(groupId: Long): Int {
        return groupMemberRepository.countByGroup_Id(groupId)
    }

}
