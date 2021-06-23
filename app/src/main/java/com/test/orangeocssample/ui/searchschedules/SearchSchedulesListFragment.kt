package com.test.orangeocssample.ui.searchschedules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.widget.textChanges
import com.test.orangeocssample.data.DefaultOcsRepository
import com.test.orangeocssample.data.ScheduleMapper
import com.test.orangeocssample.data.SearchScheduleInteractor
import com.test.orangeocssample.data.api.OcsService
import com.test.orangeocssample.databinding.FragmentListScheduleBinding
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class SearchSchedulesListFragment : Fragment() {
    private lateinit var binding: FragmentListScheduleBinding
    private lateinit var searchSchedulesViewModel: SearchSchedulesViewModel
    private val schedulesAdapter = SchedulesAdapter()

    private val compositeDisposable = CompositeDisposable()

    private var offset = PAGE_LENGTH

    // View initialization logic
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListScheduleBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initAdapter()
        initScrollListener()
        setUpSearchEditText()

        observeSearchViewModel()
    }

    private fun initViewModel() {
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
    }

    private fun setUpSearchEditText() {
        binding.searchSchedulesEditText.textChanges()
            .skip(1)
            .map { it.toString() }
            .doOnNext {
                binding.progressBar.visibility = View.VISIBLE
                binding.list.visibility = View.GONE
            }
            .debounce(800, TimeUnit.MILLISECONDS)
            .map {
                search(it)
            }
            .doOnEach {
                binding.progressBar.visibility = View.GONE
                binding.list.visibility = View.VISIBLE
            }
            .retry()
            .subscribe()
    }

    private fun observeSearchViewModel() {
        searchSchedulesViewModel.searchState.observe(requireActivity(), {
            when (it) {
                is SearchSchedulesViewModel.UiState.Error -> {
                    if (it.throwable is UnknownHostException) {
                        Toast.makeText(requireContext(), it.throwable.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                is SearchSchedulesViewModel.UiState.Success -> {
                    schedulesAdapter.submitList(ArrayList(schedulesAdapter.currentList + it.t))
                }
            }
        })
    }

    private fun createSearchRepository() = DefaultOcsRepository(
        OcsService.create(), ScheduleMapper()
    )

    private fun initAdapter() {
        binding.list.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.list.addItemDecoration(GridSpacingItemDecoration(2, 20, true))
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

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    private fun initScrollListener() {
        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.list.layoutManager as LinearLayoutManager
                if (isTheLastVisibleItem(layoutManager)) {
                    loadMore()
                }
            }
        })
    }

    /**
     * Check that we reached the last item on our list and it's not the loading state
     * which could be checked just by checking that findLastCompletelyVisibleItemPosition != -1
     */
    private fun isTheLastVisibleItem(layoutManager: LinearLayoutManager) =
        (layoutManager.findLastCompletelyVisibleItemPosition() == schedulesAdapter.itemCount - 1 && layoutManager.findLastCompletelyVisibleItemPosition() != (-1))

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

