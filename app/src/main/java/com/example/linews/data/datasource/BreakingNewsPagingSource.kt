package com.example.linews.data.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.linews.api.ApiService
import com.example.linews.model.ArticlesItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import javax.inject.Inject

class BreakingNewsPagingSource(private val apiService: ApiService) : PagingSource<Int, ArticlesItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesItem> {
        Log.e("TAG", "In LOAD: ${params.loadSize}", )

        val pageNumber = params.key ?: 1

        return try {
            val response = apiService.getBreakingNews(pageNumber = pageNumber)

            val pagedResponse = response.body()
            val data = pagedResponse?.articles

            val nextKey = if (data!!.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                pageNumber + (params.loadSize / 10)
            }

            LoadResult.Page(
                data = data.orEmpty(),
                prevKey = if(pageNumber == 1) null else pageNumber-1,
                nextKey = nextKey
            )
        } catch (e: UnknownHostException) {
            // Unable to connect to the network
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticlesItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}