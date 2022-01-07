package com.example.linews.repository

import com.example.linews.services.ApiService

class NewsRepository {

    private var api: ApiService = ApiService.getClient

    suspend fun getBreakingNews() = api.getBreakingNews()

}