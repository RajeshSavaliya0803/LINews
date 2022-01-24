package com.example.linews.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.linews.BR
import com.example.linews.databinding.ItemBreakingNewsPostBinding
import com.example.linews.databinding.ItemSavedNewsPostBinding
import com.example.linews.model.ArticlesItem
import com.example.linews.viewmodel.SavedNewsViewModel

class SavedNewsAdapter(private val viewModel: SavedNewsViewModel, private val onItemClicked: (ArticlesItem) -> Unit) : ListAdapter<ArticlesItem,SavedNewsAdapter.ViewHolder>(
    DiffCallback) {

    inner class ViewHolder(private val binding: ItemSavedNewsPostBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: ArticlesItem){
            binding.setVariable(BR.articleItem,item)
            binding.setVariable(BR.viewModel,viewModel)
            binding.executePendingBindings()
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ArticlesItem>() {
            override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSavedNewsPostBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentArticle = getItem(position)
        holder.bind(currentArticle)
    }
}