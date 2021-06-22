package com.test.orangeocssample.ui.searchschedules

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.test.orangeocssample.databinding.ActivityMainBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchSchedulesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchSchedulesViewModel: SearchSchedulesViewModel
    private val schedulesAdapter = SchedulesAdapter()

    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // create our view model
        searchSchedulesViewModel = ViewModelProvider(this).get(SearchSchedulesViewModel::class.java)

        initAdapter()

        binding.searchSchedulesEditText.doOnTextChanged { text, start, before, count ->
            search(text.toString())
        }
    }

    private fun initAdapter() {
        binding.list.layoutManager = GridLayoutManager(this, 2)
        binding.list.adapter = schedulesAdapter

        schedulesAdapter.addLoadStateListener { loadState ->
            val isEmptyList =
                loadState.refresh is LoadState.NotLoading && schedulesAdapter.itemCount == 0
            if (isEmptyList) {
                lifecycleScope.launch {
                    schedulesAdapter.submitData(PagingData.empty())
                }

            }

            // Only show the list if refresh succeeds
            binding.list.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error
        }

        binding.retryButton.setOnClickListener {
            schedulesAdapter.retry()
        }
    }


    private fun search(query: String) {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            searchSchedulesViewModel
                .searchSchedules(query)
                .collectLatest {
                    schedulesAdapter.submitData(it)
                }
        }


    }

}

