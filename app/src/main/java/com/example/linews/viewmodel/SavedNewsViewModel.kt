package com.example.linews.viewmodel

import androidx.lifecycle.*
import com.example.linews.data.repository.NewsRepository
import com.example.linews.model.ArticlesItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedNewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    //Get savedNews from repository
    val savedNews: Flow<List<ArticlesItem>> get() = repository.savedNewsFlow

    fun removeFromSaved(articlesItem: ArticlesItem) = viewModelScope.launch { repository.removeFromSaved(articlesItem) }


}