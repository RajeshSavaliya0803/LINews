package com.example.linews.view.fragment

import android.content.Intent
import android.net.Uri
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
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.linews.BR
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.savedNews.flowWithLifecycle(lifecycle,Lifecycle.State.STARTED).collect {
                adapter.submitList(it)
                Log.e("TAG", "onViewCreated: ${it.isNotEmpty()}", )
                binding.setVariable(BR.hasSavedData,it.isNotEmpty())
                binding.executePendingBindings()
            }
        }

    }

    private fun setupRecyclerView() {
        binding.rvSavedNews.layoutManager = LinearLayoutManager(activity,RecyclerView.VERTICAL,false)
        adapter = SavedNewsAdapter({remove ->
            viewModel.removeFromSaved(remove)
            Toast.makeText(activity, "Bookmarked removed", Toast.LENGTH_SHORT).show()
        },{url -> val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)})
        binding.rvSavedNews.adapter = adapter

    }
}