package com.syncup.syncup_backend.services

import com.syncup.syncup_backend.dto.CreateGroupRequestDto
import com.syncup.syncup_backend.dto.FetchGroupsDto
import com.syncup.syncup_backend.dto.GroupDto
import com.syncup.syncup_backend.entity.GroupMemberEntity
import com.syncup.syncup_backend.exceptions.UserNotFoundException
import com.syncup.syncup_backend.exceptions.UserNotFoundByEmailException
import com.syncup.syncup_backend.repositories.GroupMemberRepository
import com.syncup.syncup_backend.repositories.GroupRepository
import com.syncup.syncup_backend.repositories.UserRepository
import com.syncup.syncup_backend.toDto
import com.syncup.syncup_backend.toEntity
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class GroupService(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository,
    private val groupMemberRepository: GroupMemberRepository
) {
    fun getGroups(id: Long): List<FetchGroupsDto> {
        return groupRepository.findUserGroups(id).map { it.toDto() }
    }

    @Transactional
    fun createGroup(createGroupRequest: CreateGroupRequestDto, userId: Long): FetchGroupsDto {
        val group = groupRepository.save(createGroupRequest.toEntity())
        val invitedEmails = createGroupRequest.invitedEmails
        val user = userRepository.findById(userId).orElseThrow { UserNotFoundException(userId) }
        groupMemberRepository.save(GroupMemberEntity(group, user))
        for (email in invitedEmails) {
            val invitedUser = userRepository.findByEmail(email)
            if (invitedUser != null) {
                groupMemberRepository.save(GroupMemberEntity(group, invitedUser))
            }
            else{
                throw UserNotFoundByEmailException(email)
            }
        }
        return group.toDto()
    }
}
