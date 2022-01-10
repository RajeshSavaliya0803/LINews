package com.example.linews.services

import com.example.linews.model.NewsApiResponse
import com.example.linews.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    companion object {

        private val retrofitClient by lazy {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val getClient by lazy {
            retrofitClient.create(ApiService::class.java)
        }
    }

    @Headers("x-api-key:${Constants.API_KEY}")
    @GET("top-headlines")
    suspend fun getBreakingNews(
        @Query("country") country: String = "in",
        @Query("page") pageNumber: Int = 1,
    ): Response<NewsApiResponse>

}