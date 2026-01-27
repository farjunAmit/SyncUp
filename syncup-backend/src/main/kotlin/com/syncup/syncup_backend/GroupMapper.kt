package com.syncup.syncup_backend

import com.syncup.syncup_backend.dto.CreateGroupRequestDto
import com.syncup.syncup_backend.dto.GroupSummaryDto
import com.syncup.syncup_backend.entity.GroupEntity

fun GroupEntity.toGroupDto(): GroupSummaryDto {
    return GroupSummaryDto(
        id = this.id,
        name = this.name,
    )
}

fun GroupSummaryDto.toGroupEntity(): GroupEntity{
    return GroupEntity(
        name = this.name,
    )
}

fun CreateGroupRequestDto.toGroupEntity(): GroupEntity{
    return GroupEntity(
        name = this.name,
    )
}