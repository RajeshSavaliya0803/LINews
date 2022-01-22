package com.example.linews.data.repository


import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.linews.api.ApiService
import com.example.linews.data.database.SavedNewsDao
import com.example.linews.data.pagedSource.BreakingNewsPagingSource
import com.example.linews.model.ArticlesItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val apiService: ApiService, private val dao: SavedNewsDao) {

    // assign method from remote data source
    fun getBreakingNews() : Flow<PagingData<ArticlesItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 3,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {
                BreakingNewsPagingSource(apiService)
            }
        ).flow
    }


     suspend  fun addToBookmark (article: ArticlesItem) = withContext(Dispatchers.IO){
         Log.e("TAG", "Repo=> addBookmark: ${article.title}", )
         Log.e("TAG", "Repo=> addBookmark: ${Thread.currentThread().name}", )
         dao.addBookMark(article)
     }



}