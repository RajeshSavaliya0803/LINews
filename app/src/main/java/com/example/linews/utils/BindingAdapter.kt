package com.example.linews.utils

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.ImageRequest
import com.example.linews.R
import com.example.linews.adapter.BreakingNewsAdapter
import com.example.linews.model.ArticlesItem
import com.example.linews.viewmodel.BreakingNewsUiState

@BindingAdapter("setAdapter")
fun setAdapter(recyclerView: RecyclerView, items: List<ArticlesItem?>?){
    recyclerView.adapter = BreakingNewsAdapter(items)
}

@BindingAdapter("imageUrl")
fun imageUrl(imageView: ImageView, imageUrl: String?){
    imageView.load(imageUrl){
        crossfade(true)
        listener(
            onError = {_, throwable ->
                Log.e("TAG", "imageUrl:OnError $imageUrl, ${throwable.message} ")
            }

        )
    }
}

@BindingAdapter("setNoNewsVisibility")
fun setNoNewsVisibility(textView: TextView, uiState: BreakingNewsUiState){
    if(!uiState.isLoading && uiState.breakNewsItems!!.isEmpty()){
        textView.visibility = View.VISIBLE
    }else{
        textView.visibility = View.GONE
    }
}