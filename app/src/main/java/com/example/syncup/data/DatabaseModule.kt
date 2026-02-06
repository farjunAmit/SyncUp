package com.example.syncup.data

import android.content.Context
import androidx.room.Room
import com.example.syncup.data.local.GroupDao
import com.example.syncup.data.local.SyncUpLocalDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SyncUpLocalDB {
        return Room.databaseBuilder(
            context,
            SyncUpLocalDB::class.java,
            "syncup_db"
        ).build()
    }

    @Provides
    fun provideGroupDao(database: SyncUpLocalDB): GroupDao {
        return database.groupDao()
    }
}