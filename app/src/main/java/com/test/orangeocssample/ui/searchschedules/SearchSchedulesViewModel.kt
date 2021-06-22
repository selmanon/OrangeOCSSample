package com.test.orangeocssample.ui.searchschedules

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.orangeocssample.data.SearchScheduleInteractor
import com.test.orangeocssample.domaine.Schedule

class SearchSchedulesViewModel(
    private val searchScheduleInteractor: SearchScheduleInteractor,
    private val scheduler: SchedulerWrapper
) : ViewModel() {

    private val _searchUiState: MutableLiveData<UiState> =
        MutableLiveData()
    val searchState: LiveData<UiState> get() = _searchUiState

    fun searchSchedules(titleQuery: String, offset: Int) =
        searchScheduleInteractor.getSchedule(titleQuery, offset)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.main())
            .subscribe({
                _searchUiState.postValue(UiState.Success(it))
            }, {
                _searchUiState.postValue(UiState.Error(it.message!!))
            })

    sealed class UiState {
        data class Error(val message: String) : UiState()
        data class Success(val t: List<Schedule>) : UiState()
    }
}