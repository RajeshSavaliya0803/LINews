package com.example.linews.utils

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.linews.R
import com.example.linews.adapter.BreakingNewsAdapter
import com.example.linews.model.ArticlesItem

@BindingAdapter("setAdapter")
fun setAdapter(recyclerView: RecyclerView, items: List<ArticlesItem?>?){
    recyclerView.adapter = BreakingNewsAdapter(items)
}

@BindingAdapter("imageUrl")
fun setImage(view: ImageView, imageUrl: String?){
    view.load(imageUrl){
        crossfade(true)
        error(R.mipmap.ic_launcher)
    }
}