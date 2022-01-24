package com.example.linews.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.linews.model.ArticlesItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.http.DELETE

@Dao
interface SavedNewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookMark(article: ArticlesItem) : Long

    @Query("SELECT * FROM article")
    fun getSavedNews() : Flow<List<ArticlesItem>>

    @Delete
    suspend  fun removeSavedNote(article: ArticlesItem)

}