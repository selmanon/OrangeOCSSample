package com.test.orangeocssample.ui.searchschedules

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.test.orangeocssample.databinding.ActivityMainBinding
import io.reactivex.disposables.CompositeDisposable

class SearchSchedulesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchSchedulesViewModel: SearchSchedulesViewModel
    private val schedulesAdapter = SchedulesAdapter()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

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

        searchSchedulesViewModel.searchState.observe(this, Observer {
            when (it) {
                is SearchSchedulesViewModel.LceState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is SearchSchedulesViewModel.LceState.Error -> {
                    binding.retryButton.visibility = View.VISIBLE
                }

                is SearchSchedulesViewModel.LceState.Content -> {
                    schedulesAdapter.submitList(listOf(it.t))
                }
            }
        })
    }

    private fun initAdapter() {
        binding.list.layoutManager = GridLayoutManager(this, 2)
        binding.list.adapter = schedulesAdapter
    }

    private fun search(query: String) {
        compositeDisposable.add(
            searchSchedulesViewModel
                .searchSchedules(query, DEFAULT_INITIAL_OFFSET)
        )
    }

    companion object {
        const val DEFAULT_INITIAL_OFFSET = 1
    }
}

