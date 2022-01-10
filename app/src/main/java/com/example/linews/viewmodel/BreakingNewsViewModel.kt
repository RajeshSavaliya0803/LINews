package com.example.linews.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linews.model.NewsApiResponse
import com.example.linews.repository.NewsRepository
import com.example.linews.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class BreakingNewsViewModel(private  var repository: NewsRepository) : ViewModel() {

    private var _breakingNewsData : MutableLiveData<UIState<NewsApiResponse>>
    val breakingNewsData: LiveData<UIState<NewsApiResponse>> get() = _breakingNewsData

    private var _loadingState : MutableLiveData<Boolean>
    val loadingState: LiveData<Boolean> get() = _loadingState

    init {
        Log.e("TAG","BreakingNewsViewModel initialized")
        _breakingNewsData = MutableLiveData()
        _loadingState = MutableLiveData()

        fetchBreakingNews()
    }

    private fun fetchBreakingNews() {
        _loadingState.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getBreakingNews()
            withContext(Dispatchers.Main){
                _loadingState.value = false
                handleBreakingNewsResponse(response)
            }
        }
    }

    private fun handleBreakingNewsResponse(response: Response<NewsApiResponse>) : UIState<NewsApiResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return UIState.Success(resultResponse)
            }
        }
        return UIState.Failed(response.message())
    }

}