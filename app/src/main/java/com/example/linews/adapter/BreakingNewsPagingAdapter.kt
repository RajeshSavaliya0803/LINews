package com.example.linews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.linews.BR
import com.example.linews.databinding.ItemBreakingNewsPostBinding
import com.example.linews.model.ArticlesItem
import com.example.linews.viewmodel.BreakingNewsViewModel
import okhttp3.internal.notify

class BreakingNewsPagingAdapter(private val onBookmarked: (article: ArticlesItem) -> Unit, private val onNewsClicked : (urls:String?) -> Unit) : PagingDataAdapter<ArticlesItem, BreakingNewsPagingAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val itemBreakingNewsPostBinding: ItemBreakingNewsPostBinding) : RecyclerView.ViewHolder(itemBreakingNewsPostBinding.root){
        fun bind(item : ArticlesItem?){
            itemBreakingNewsPostBinding.setVariable(BR.articleItem,item)
            itemBreakingNewsPostBinding.removeNews
            itemBreakingNewsPostBinding.executePendingBindings()
            itemBreakingNewsPostBinding.root.setOnClickListener {
                onNewsClicked(item?.url)
            }
            itemBreakingNewsPostBinding.removeNews.setOnClickListener {
                onBookmarked(item!!)
                val snapShotNews = this@BreakingNewsPagingAdapter.snapshot().firstOrNull{snapShotArticle ->
                    snapShotArticle?.title ==item.title
                }

                if(snapShotNews != null){
                    snapShotNews.bookmarked = true
                }
            }
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