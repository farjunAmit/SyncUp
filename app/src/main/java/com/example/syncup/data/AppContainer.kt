package com.example.syncup.data

import android.content.Context
import com.example.syncup.data.repository.auth.DefaultAuthRepository
import com.example.syncup.data.repository.event.InMemoryEventRepository
import com.example.syncup.data.repository.group.DefaultGroupRemoteDataSource
import com.example.syncup.data.repository.group.DefaultGroupsRepository
import com.example.syncup.data.repository.group.GroupApi
import com.example.syncup.data.repository.group.GroupsRepository
import com.example.syncup.data.session.SessionStore
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
class AppContainer(
) {
    val eventRepository = InMemoryEventRepository()

}
