package com.example.linews.repository

import android.util.Log
import com.example.linews.api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getBreakingNews() = withContext(Dispatchers.IO){
        Log.e("TAG", "Repository Thread: ${Thread.currentThread().name}")
        apiService.getBreakingNews()
    }

}