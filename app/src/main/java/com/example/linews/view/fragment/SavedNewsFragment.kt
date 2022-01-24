package com.example.linews.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.linews.R
import com.example.linews.adapter.SavedNewsAdapter
import com.example.linews.databinding.FragmentBreakingNewsBinding
import com.example.linews.databinding.FragmentSavedNewsBinding
import com.example.linews.viewmodel.BreakingNewsViewModel
import com.example.linews.viewmodel.SavedNewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedNewsFragment : Fragment() {

    private lateinit var binding: FragmentSavedNewsBinding
    private val viewModel : SavedNewsViewModel by viewModels()
    private lateinit var adapter: SavedNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_saved_news, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.savedNews.collect {
                    adapter.submitList(it)
                }
            }
        }

    }

    private fun setupRecyclerView() {
        binding.rvSavedNews.layoutManager = LinearLayoutManager(activity,RecyclerView.VERTICAL,false)
        adapter = SavedNewsAdapter(viewModel){

        }
        binding.rvSavedNews.adapter = adapter
    }
}