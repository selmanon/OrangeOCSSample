package com.test.orangeocssample.ui.searchschedules

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.orangeocssample.data.DefaultOcsRepository
import com.test.orangeocssample.data.ScheduleMapper
import com.test.orangeocssample.data.api.OcsService
import com.test.orangeocssample.domaine.OcsRepository
import com.test.orangeocssample.domaine.Schedule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchSchedulesViewModel() : ViewModel() {

    private val _searchLceState: MutableLiveData<LceState> =
        MutableLiveData()
    val searchState: LiveData<LceState> get() = _searchLceState


    private val ocsRepository: OcsRepository = DefaultOcsRepository(
        OcsService.create(),
        ScheduleMapper()
    )


    fun searchSchedules(titleQuery: String, offset: Int) =
        ocsRepository.getSchedulesStreamBy(titleQuery, offset)
            .doOnSubscribe {
                _searchLceState.postValue(LceState.Loading)
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _searchLceState.postValue(LceState.Content(it))
            }, {
                _searchLceState.postValue(LceState.Error)
            })

    sealed class LceState {
        object Loading : LceState()
        object Error : LceState()
        data class Content(val t: Schedule) : LceState()
    }
}