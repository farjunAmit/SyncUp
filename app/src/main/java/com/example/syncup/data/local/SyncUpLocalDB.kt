package com.example.syncup.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GroupEntity::class], version = 1)
abstract class SyncUpLocalDB : RoomDatabase() {
    abstract fun groupDao(): GroupDao
}