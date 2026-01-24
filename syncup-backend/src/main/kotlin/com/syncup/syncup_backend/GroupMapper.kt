package com.syncup.syncup_backend

import com.syncup.syncup_backend.dto.CreateGroupRequestDto
import com.syncup.syncup_backend.dto.FetchGroupsDto
import com.syncup.syncup_backend.entity.GroupEntity

fun GroupEntity.toDto(): FetchGroupsDto {
    return FetchGroupsDto(
        id = this.id,
        name = this.name,
    )
}

fun FetchGroupsDto.toEntity(): GroupEntity{
    return GroupEntity(
        name = this.name,
    )
}

fun CreateGroupRequestDto.toEntity(): GroupEntity{
    return GroupEntity(
        name = this.name,
    )
}