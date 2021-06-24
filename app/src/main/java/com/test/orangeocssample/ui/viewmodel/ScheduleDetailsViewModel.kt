package com.test.orangeocssample.ui.searchschedules

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.orangeocssample.data.interactor.ScheduleDetailsInteractor
import com.test.orangeocssample.domaine.ScheduleDetails
import com.test.orangeocssample.utils.rxjava.SchedulerWrapper

@Suppress("UNCHECKED_CAST")
class ScheduleDetailsViewModelFactory(
    private val scheduleDetailsInteractor: ScheduleDetailsInteractor,
    private val scheduler: SchedulerWrapper
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return (ScheduleDetailsViewModel(scheduleDetailsInteractor, scheduler) as T)
    }
}


class ScheduleDetailsViewModel(
    private val scheduleDetailsInteractor: ScheduleDetailsInteractor,
    private val scheduler: SchedulerWrapper
) : ViewModel() {

    private val _scheduleDetailUiState: MutableLiveData<UiState> =
        MutableLiveData()
    val scheduleDetailsState: LiveData<UiState> get() = _scheduleDetailUiState

    fun getScheduleDetails(detailLink: String) =
        scheduleDetailsInteractor.getScheduleDetails(detailLink)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.main())
            .subscribe({
                _scheduleDetailUiState.value = UiState.Success(it)
            }, {
                _scheduleDetailUiState.value = UiState.Error(it)
            })

    sealed class UiState {
        data class Error(val throwable: Throwable) : UiState()
        data class Success(val data: ScheduleDetails) : UiState()
    }
}