package com.example.linews.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.linews.R
import com.example.linews.adapter.BreakingNewsPagingAdapter
import com.example.linews.adapter.NetWorkStateAdapter
import com.example.linews.databinding.FragmentBreakingNewsBinding
import com.example.linews.viewmodel.BreakingNewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BreakingNewsFragment : Fragment() {

    private lateinit var binding: FragmentBreakingNewsBinding
    private val viewModel: BreakingNewsViewModel by viewModels()
    private lateinit var adapter: BreakingNewsPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_breaking_news, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getBreakingNews().flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    adapter.submitData(it)
                }
        }
    }

    private fun setupRecyclerView() {

        binding.rvBreakingNews.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        adapter =
            BreakingNewsPagingAdapter({ save ->
                viewModel.addBookmark(save)
                Toast.makeText(activity, "Bookmarked article", Toast.LENGTH_SHORT).show()
            }, { open -> })

        binding.rvBreakingNews.adapter =
            adapter.withLoadStateFooter(NetWorkStateAdapter { adapter.retry() })

        adapter.addLoadStateListener { listener ->
            when (listener.refresh) {
                is LoadState.NotLoading -> {
                    if (binding.refreshing == true) {
                        binding.refreshing = false
                        binding.hasData = adapter.itemCount != 0
                    }
                }
                LoadState.Loading -> {
                    binding.refreshing = true
                }
                is LoadState.Error -> {
                    Toast.makeText(
                        context,
                        (listener.refresh as LoadState.Error).error.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}