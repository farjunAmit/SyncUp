package com.example.syncup.data

import com.example.syncup.data.remote.group.DefaultGroupRemoteDataSource
import com.example.syncup.data.repository.group.DefaultGroupRepository
import com.example.syncup.data.remote.group.GroupRemoteDataSource
import com.example.syncup.data.repository.group.GroupsRepository
import com.example.syncup.data.repository.auth.AuthRepository
import com.example.syncup.data.repository.auth.DefaultAuthRepository
import com.example.syncup.data.repository.event.DefaultEventRemoteDataSource
import com.example.syncup.data.repository.event.DefaultEventRepository
import com.example.syncup.data.repository.event.EventRemoteDataSource
import com.example.syncup.data.repository.event.EventRepository
import dagger.hilt.components.SingletonComponent
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindGroupsRepository(
        groupsRepository: DefaultGroupRepository
    ): GroupsRepository
    @Binds
    abstract fun bindGroupRemoteDataSource(
        groupRemoteDataSource: DefaultGroupRemoteDataSource
    ): GroupRemoteDataSource

    @Binds
    abstract fun bindAuthRepository(
        authRepository: DefaultAuthRepository
    ): AuthRepository

    @Binds
    abstract fun bindEventRepository(
        eventRepository: DefaultEventRepository
    ): EventRepository

    @Binds
    abstract fun bindEventRemoteDataSource(
        eventRemoteDataSource: DefaultEventRemoteDataSource
    ): EventRemoteDataSource


}