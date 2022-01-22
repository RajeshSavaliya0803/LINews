package com.example.linews.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.linews.model.ArticlesItem

@Database(entities = [ArticlesItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun savedNewsDao () : SavedNewsDao
}