package com.example.linews.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.linews.R
import com.example.linews.databinding.FragmentBreakingNewsBinding
import com.example.linews.repository.NewsRepository
import com.example.linews.utils.UIState
import com.example.linews.viewmodel.BreakingNewsViewModel
import com.example.linews.viewmodel.BreakingNewsViewModelFactory

class BreakingNewsFragment : Fragment() {

    private lateinit var binding: FragmentBreakingNewsBinding
    private lateinit var model: BreakingNewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_breaking_news, container, false)
        model = ViewModelProvider(this,BreakingNewsViewModelFactory(NewsRepository()))[BreakingNewsViewModel::class.java]
        binding.viewModel = model
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.breakingNewsData.observe(viewLifecycleOwner, { state->
            when(state){
                is UIState.Failed -> {
                }
                is UIState.Loading -> {
                }
                is UIState.Success-> {
                }
            }
        })
    }
}