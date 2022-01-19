package com.example.linews.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.linews.adapter.BreakingNewsPagingAdapter
import com.example.linews.data.datasource.BreakingNewsPagingSource
import com.example.linews.model.ArticlesItem
import com.example.linews.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


data class BreakingNewsUiState(
    val isLoading : Boolean = true,
    val breakNewsItems : List<ArticlesItem?>? = listOf(),
    var errorMessage : String? = null
)

@HiltViewModel
class BreakingNewsViewModel @Inject constructor(private  var repository: NewsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(BreakingNewsUiState())
    val uiState: StateFlow<BreakingNewsUiState> = _uiState.asStateFlow()
    lateinit var adapter: BreakingNewsPagingAdapter


    init {
        setUpAdapter()
        fetchBreakingNews()
    }

    private fun setUpAdapter() {
        //TODO: Try loadState here
        adapter = BreakingNewsPagingAdapter()
        adapter.addLoadStateListener {

        }

    }

    private fun fetchBreakingNews() {
        Log.e("TAG", "In Fetch Breaking News")
        viewModelScope.launch {
            repository.getBreakingNews().cachedIn(viewModelScope).collect {
                adapter.submitData(it)

            }
        }
    }

//    private fun fetchBreakingNews() {
//
//        viewModelScope.launch{
//            try{
//                val response = repository.getBreakingNews()
//                if(response.isSuccessful){
//                    _uiState.update {
//                        it.copy(
//                            isLoading = false,
//                            breakNewsItems = response.body()?.articles,
//                            errorMessage = null
//                        )
//                    }
//                }else{
//                    _uiState.update {
//                        it.copy(
//                            isLoading = false,
//                            breakNewsItems = emptyList(),
//                            errorMessage = "Something went wrong"
//                        )
//                    }
//                }
//
//            }catch (e: IOException){
//                _uiState.update {
//                    it.copy(
//                        isLoading = false,
//                        breakNewsItems = emptyList(),
//                        errorMessage = "${e.message}"
//                    )
//                }
//            }
//        }
//    }
}