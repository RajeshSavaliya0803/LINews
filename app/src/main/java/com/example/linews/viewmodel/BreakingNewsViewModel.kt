package com.example.linews.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linews.model.ArticlesItem
import com.example.linews.model.NewsApiResponse
import com.example.linews.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
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

    init {
        Log.e("TAG","BreakingNewsViewModel initialized")
        fetchBreakingNews()
    }

    private fun fetchBreakingNews() {

        viewModelScope.launch{

            Log.e("TAG", "ViewModel Thread: ${Thread.currentThread().name} ", )
            try{
                val response = repository.getBreakingNews()
                if(response.isSuccessful){
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            breakNewsItems = response.body()?.articles,
                            errorMessage = null
                        )
                    }
                }else{
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            breakNewsItems = emptyList(),
                            errorMessage = "Something went wrong"
                        )
                    }
                }

            }catch (e: IOException){
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        breakNewsItems = emptyList(),
                        errorMessage = "${e.message}"
                    )
                }
            }
        }
    }
}