package com.syncup.syncup_backend

import com.syncup.syncup_backend.dto.CreateGroupRequestDto
import com.syncup.syncup_backend.dto.GroupSummaryDto
import com.syncup.syncup_backend.entity.GroupEntity

fun GroupEntity.toDto(): GroupSummaryDto {
    return GroupSummaryDto(
        id = this.id,
        name = this.name,
    )
}

fun GroupSummaryDto.toEntity(): GroupEntity{
    return GroupEntity(
        name = this.name,
    )
}

fun CreateGroupRequestDto.toEntity(): GroupEntity{
    return GroupEntity(
        name = this.name,
    )
}