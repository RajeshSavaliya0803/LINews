package com.example.linews.data.pagedSource


import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.linews.api.ApiService
import com.example.linews.model.ArticlesItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException


class BreakingNewsPagingSource(private val apiService: ApiService) : PagingSource<Int, ArticlesItem>() {

    override fun getRefreshKey(state: PagingState<Int, ArticlesItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesItem> {

        val pageNumber = params.key ?: 1

        return try {

            val response = withContext(Dispatchers.IO){
                apiService.getBreakingNews(pageNumber = pageNumber)
            }

            val pagedResponse = response.body()
            val data = pagedResponse?.articles

            val nextKey = if (data!!.isEmpty()) {
                null
            } else {
                pageNumber + 1
            }

            LoadResult.Page(
                data = data.orEmpty(),
                prevKey = if(pageNumber == 1) null else pageNumber-1,
                nextKey = nextKey
            )
        } catch (e: UnknownHostException) {
            // Unable to connect to the network
            Log.e("TAG", "Load Exception: $e ", )
            LoadResult.Error(e)
        }
    }


}