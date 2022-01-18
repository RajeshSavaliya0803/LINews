package com.example.linews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.linews.BR
import com.example.linews.databinding.ItemBreakingNewsPostBinding
import com.example.linews.model.ArticlesItem

class BreakingNewsAdapter(private val newsList: List<ArticlesItem?>?) : RecyclerView.Adapter<BreakingNewsAdapter.ViewHolder>() {

    inner class ViewHolder(private val itemBreakingNewsPostBinding: ItemBreakingNewsPostBinding) : RecyclerView.ViewHolder(itemBreakingNewsPostBinding.root){
        fun bind(item : ArticlesItem?){
            itemBreakingNewsPostBinding.setVariable(BR.articleItem,item)
            itemBreakingNewsPostBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BreakingNewsAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemBreakingNewsPostBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BreakingNewsAdapter.ViewHolder, position: Int) {
        val currentArticle = newsList!![position]
        holder.bind(currentArticle)
    }

    override fun getItemCount(): Int {
        return newsList!!.size
    }
}