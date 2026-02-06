package com.example.syncup.data.mapper

import com.example.syncup.data.dto.GroupSummaryDto
import com.example.syncup.data.local.GroupEntity
import com.example.syncup.data.model.groups.Group

fun GroupSummaryDto.toGroup(): Group {
    return Group(
        id = id,
        name = name
    )
}

fun GroupEntity.toGroup(): Group {
    return Group(
        id = id,
        name = name
    )
}

fun GroupSummaryDto.toGroupEntity(): GroupEntity {
    return GroupEntity(
        id = id,
        name = name
    )
}