package com.example.linews.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.linews.model.ArticlesItem

@Database(entities = [ArticlesItem::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun savedNewsDao () : SavedNewsDao
}