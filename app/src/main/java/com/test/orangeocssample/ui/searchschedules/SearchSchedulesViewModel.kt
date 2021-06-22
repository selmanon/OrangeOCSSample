package com.test.orangeocssample.ui.searchschedules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.orangeocssample.data.DefaultOcsRepository
import com.test.orangeocssample.data.ScheduleMapper
import com.test.orangeocssample.data.api.OcsService
import com.test.orangeocssample.data.api.Result
import com.test.orangeocssample.domaine.OcsRepository
import com.test.orangeocssample.domaine.Schedule
import kotlinx.coroutines.flow.Flow

class SearchSchedulesViewModel() : ViewModel() {
    private val ocsRepository: OcsRepository = DefaultOcsRepository(OcsService.create(),ScheduleMapper())

    private var currentTitleQuery: String? = null
    private var currentSearchResult: Flow<PagingData<Schedule>>? = null

    fun searchSchedules(titleQuery: String): Flow<PagingData<Schedule>> {
        val lastSearchResult = currentSearchResult
        if (titleQuery == currentTitleQuery && lastSearchResult != null) {
            return lastSearchResult
        }

        currentTitleQuery = titleQuery
        val newSearchResult = ocsRepository
            .getSchedulesStreamBy(titleQuery)
            .cachedIn(viewModelScope)

        currentSearchResult = newSearchResult

        return newSearchResult
    }


}