package com.example.linews.di

import android.content.Context
import androidx.room.Room
import com.example.linews.data.database.AppDatabase
import com.example.linews.data.database.SavedNewsDao
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
    fun provideAppDatabase(@ApplicationContext appContext: Context) : AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java,"li_news_db").fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideSavedNewsDao(appDatabase: AppDatabase): SavedNewsDao{
        return appDatabase.savedNewsDao()
    }
}