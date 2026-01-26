package com.example.syncup.data

import com.example.syncup.data.repository.group.DefaultGroupRemoteDataSource
import com.example.syncup.data.repository.group.DefaultGroupsRepository
import com.example.syncup.data.repository.group.GroupRemoteDataSource
import com.example.syncup.data.repository.group.GroupsRepository
import com.example.syncup.data.repository.auth.AuthRepository
import com.example.syncup.data.repository.auth.DefaultAuthRepository
import dagger.hilt.components.SingletonComponent
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindGroupsRepository(
        groupsRepository: DefaultGroupsRepository
    ): GroupsRepository
    @Binds
    abstract fun bindGroupRemoteDataSource(
        groupRemoteDataSource: DefaultGroupRemoteDataSource
    ): GroupRemoteDataSource

    @Binds
    abstract fun bindAuthRepository(
        authRepository: DefaultAuthRepository
    ): AuthRepository
}