package com.syncup.syncup_backend

import com.syncup.syncup_backend.dto.AddGroupMemberDto

fun AddGroupMemberDto.toEntity(): AddGroupMemberDto{
    return AddGroupMemberDto(
        id = this.id,
        groupId = this.groupId,
        userId = this.userId
}