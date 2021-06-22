package com.test.orangeocssample.ui.searchschedules

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.orangeocssample.data.DefaultOcsRepository
import com.test.orangeocssample.data.ScheduleMapper
import com.test.orangeocssample.data.SearchScheduleInteractor
import com.test.orangeocssample.data.api.OcsService
import com.test.orangeocssample.databinding.ActivityMainBinding
import io.reactivex.disposables.CompositeDisposable

class SearchSchedulesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchSchedulesViewModel: SearchSchedulesViewModel
    private val schedulesAdapter = SchedulesAdapter()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var offset = PAGE_LENGTH

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // create our view model
        val searchSchedulesViewModelFactory = SearchSchedulesViewModelFactory(
            SearchScheduleInteractor(
                createSearchRepository()
            ), DefaultScheduler()
        )

        searchSchedulesViewModel =
            ViewModelProvider(
                this,
                searchSchedulesViewModelFactory
            ).get(SearchSchedulesViewModel::class.java)

        initAdapter()
        initScrollListener()

        binding.searchSchedulesEditText.doOnTextChanged { text, start, before, count ->
            search(text.toString())
        }


        searchSchedulesViewModel.searchState.observe(this, {
            when (it) {
                is SearchSchedulesViewModel.UiState.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }

                is SearchSchedulesViewModel.UiState.Success -> {
                    schedulesAdapter.submitList(schedulesAdapter.currentList + it.t)
                }
            }
        })
    }

    private fun createSearchRepository() = DefaultOcsRepository(
        OcsService.create(), ScheduleMapper()
    )

    private fun initAdapter() {
        binding.list.layoutManager = GridLayoutManager(this, 2)
        binding.list.adapter = schedulesAdapter
    }

    private fun search(query: String) {
        schedulesAdapter.submitList(null)
        offset = 0
        compositeDisposable.add(
            searchSchedulesViewModel
                .searchSchedules(query, DEFAULT_INITIAL_OFFSET)
        )
    }


    private fun initScrollListener() {
        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.list.layoutManager as LinearLayoutManager
                if (layoutManager.findLastCompletelyVisibleItemPosition() == schedulesAdapter.itemCount - 1) {
                    loadMore()
                }
            }
        })
    }


    fun loadMore() {
        offset += PAGE_LENGTH
        searchSchedulesViewModel.searchSchedules(
            binding.searchSchedulesEditText.text.toString(),
            offset
        )
    }

    companion object {
        const val DEFAULT_INITIAL_OFFSET = 1
        const val PAGE_LENGTH = 30
    }
}

