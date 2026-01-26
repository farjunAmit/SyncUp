package com.example.syncup.data.repository.group

import com.example.syncup.data.dto.GroupSummaryDto
import com.example.syncup.data.model.groups.Group

fun GroupSummaryDto.toGroup(): Group {
    return Group(
        id = id,
        name = name
    )
}