package com.syncup.syncup_backend.exceptions

class GroupNotFoundException(val groupId: Long) :
    RuntimeException("Group with id $groupId as id not found")