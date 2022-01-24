package com.example.linews.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.linews.model.ArticlesItem
import com.example.linews.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class BreakingNewsViewModel @Inject constructor(private  var repository: NewsRepository) : ViewModel() {


    fun getBreakingNews(): Flow<PagingData<ArticlesItem>> = repository.getBreakingNews().cachedIn(viewModelScope)

    fun addBookmark(article: ArticlesItem){
        viewModelScope.launch {
            repository.addToBookmark(article)
        }
    }

}