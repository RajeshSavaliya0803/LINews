package com.example.linews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linews.repository.NewsRepository

class BreakingNewsViewModel : ViewModel() {

    private val  _loadingIndicator : MutableLiveData<Boolean> = MutableLiveData()
    private var repo : NewsRepository = NewsRepository()
    val loadingIndicator : LiveData<Boolean> get() = _loadingIndicator


    init {
        _loadingIndicator.value = true
    }

}