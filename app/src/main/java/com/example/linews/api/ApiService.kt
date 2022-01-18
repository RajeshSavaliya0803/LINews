package com.example.linews.api

import com.example.linews.model.NewsApiResponse
import com.example.linews.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    @Headers("x-api-key:${Constants.API_KEY}")
    @GET("top-headlines")
    suspend fun getBreakingNews(
        @Query("country") country: String = "in",
        @Query("page") pageNumber: Int = 1,
    ): Response<NewsApiResponse>

}