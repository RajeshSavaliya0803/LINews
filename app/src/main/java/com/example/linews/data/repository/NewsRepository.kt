package com.example.linews.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.linews.api.ApiService
import com.example.linews.data.datasource.BreakingNewsPagingSource
import com.example.linews.model.ArticlesItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val apiService: ApiService) {

    // assign method from remote data source
//    suspend fun getBreakingNews() = withContext(Dispatchers.IO){
//        Log.e("TAG", "Repository Thread: ${Thread.currentThread().name}")
//        apiService.getBreakingNews()
//    }

    fun getBreakingNews() : Flow<PagingData<ArticlesItem>>{
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

}