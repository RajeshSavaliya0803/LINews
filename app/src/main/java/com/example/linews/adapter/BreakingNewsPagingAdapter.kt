package com.example.linews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.linews.BR
import com.example.linews.databinding.ItemBreakingNewsPostBinding
import com.example.linews.model.ArticlesItem

class BreakingNewsPagingAdapter() : PagingDataAdapter<ArticlesItem, BreakingNewsPagingAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val itemBreakingNewsPostBinding: ItemBreakingNewsPostBinding) : RecyclerView.ViewHolder(itemBreakingNewsPostBinding.root){
        fun bind(item : ArticlesItem?){
            itemBreakingNewsPostBinding.setVariable(BR.articleItem,item)
            itemBreakingNewsPostBinding.executePendingBindings()
        }
    }

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<ArticlesItem>() {
            override fun areItemsTheSame(
                oldItem: ArticlesItem,
                newItem: ArticlesItem
            ) = oldItem.title == newItem.title

            override fun areContentsTheSame(
                oldItem: ArticlesItem,
                newItem: ArticlesItem
            ) = oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentArticle = getItem(position)
        holder.bind(currentArticle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemBreakingNewsPostBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }
}