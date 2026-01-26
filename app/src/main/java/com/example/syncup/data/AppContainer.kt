package com.example.syncup.data

import com.example.syncup.data.repository.event.InMemoryEventRepository
import com.example.syncup.data.repository.group.DefaultGroupRemoteDataSource
import com.example.syncup.data.repository.group.DefaultGroupsRepository
import com.example.syncup.data.repository.group.GroupApi
import com.example.syncup.data.repository.group.GroupsRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * AppContainer
 *
 * Simple dependency container for the application.
 * Responsible for providing app-wide dependencies.
 *
 * This container currently exposes an in-memory implementation
 * of GroupsRepository and serves as a lightweight alternative
 * to a full dependency injection framework.
 */
class AppContainer {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://127.0.0.1:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val groupApi = retrofit.create(GroupApi::class.java)
    private val groupRemote = DefaultGroupRemoteDataSource(groupApi)

    val groupsRepository: GroupsRepository = DefaultGroupsRepository(groupRemote)
    val eventRepository = InMemoryEventRepository()
}
