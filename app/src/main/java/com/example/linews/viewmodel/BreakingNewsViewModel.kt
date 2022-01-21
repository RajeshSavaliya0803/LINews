package com.example.linews.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.linews.adapter.BreakingNewsPagingAdapter
import com.example.linews.adapter.NetWorkStateAdapter
import com.example.linews.data.datasource.BreakingNewsPagingSource
import com.example.linews.model.ArticlesItem
import com.example.linews.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


data class BreakingNewsUiState(
    val initialLoading : Boolean = false,
    val hasData : Boolean?  = null,
    var errorMessage : String? = null
)

@HiltViewModel
class BreakingNewsViewModel @Inject constructor(private  var repository: NewsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(BreakingNewsUiState())
    val uiState: StateFlow<BreakingNewsUiState> = _uiState.asStateFlow()
    val adapter: BreakingNewsPagingAdapter = BreakingNewsPagingAdapter()


    init {
        fetchBreakingNews()
        loadStateListener()
    }
    private fun loadStateListener(){
        adapter.addLoadStateListener { listener ->
            when(listener.refresh){
                is LoadState.NotLoading -> {
                    if(_uiState.value.initialLoading){
                        _uiState.update {
                            it.copy(initialLoading = false, hasData = adapter.itemCount != 0)
                        }
                    }
                }
                LoadState.Loading -> {
                    _uiState.update {
                        it.copy(initialLoading = true)
                    }
                }
                is LoadState.Error -> {
                    _uiState.update {
                        it.copy(initialLoading = false, hasData = null, errorMessage = (listener.refresh as LoadState.Error).error.toString() )
                    }
                }
            }
        }
    }

    private fun fetchBreakingNews() {
        viewModelScope.launch {
            repository.getBreakingNews().cachedIn(viewModelScope).collect {
                adapter.submitData(it)

            }
        }
    }
}