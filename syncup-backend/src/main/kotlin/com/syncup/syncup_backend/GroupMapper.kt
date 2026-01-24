package com.syncup.syncup_backend

import com.syncup.syncup_backend.dto.CreateGroupRequestDto
import com.syncup.syncup_backend.dto.FetchGroupsRequestDto
import com.syncup.syncup_backend.entity.GroupEntity

fun GroupEntity.toDto(): FetchGroupsRequestDto {
    return FetchGroupsRequestDto(
        id = this.id,
        name = this.name,
    )
}

fun FetchGroupsRequestDto.toEntity(): GroupEntity{
    return GroupEntity(
        name = this.name,
    )
}

fun CreateGroupRequestDto.toEntity(): GroupEntity{
    return GroupEntity(
        name = this.name,
    )
}