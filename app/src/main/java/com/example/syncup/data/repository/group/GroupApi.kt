package com.example.syncup.data.repository.group

import com.example.syncup.data.dto.AddGroupMemberRequestDto
import com.example.syncup.data.dto.CreateGroupRequestDto
import com.example.syncup.data.dto.GroupSummaryDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface GroupApi {

    @GET("groups/{id}")
    suspend fun getGroups(@Path("id") id: Long): List<GroupSummaryDto>

    @GET("groups/get/{id}")
    suspend fun getGroup(@Path("id") id: Long): GroupSummaryDto

    @POST("groups/create/{userId}")
    suspend fun createGroup(
        @Path("userId") userId: Long,
        @Body body: CreateGroupRequestDto
    ): GroupSummaryDto

    @POST("groups/rename/{groupId}")
    suspend fun renameGroup(
        @Path("groupId") groupId: Long,
        @Body name: String
    ): GroupSummaryDto

    @DELETE("groups/delete/{groupId}")
    suspend fun deleteGroup(@Path("groupId") groupId: Long)

    @POST("groups/addMember/{groupId}")
    suspend fun addMember(
        @Path("groupId") groupId: Long,
        @Body body: AddGroupMemberRequestDto
    ): GroupSummaryDto

    @GET("groups/size/{groupId}")
    suspend fun getGroupSize(@Path("groupId") groupId: Long): Int
}
