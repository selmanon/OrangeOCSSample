package com.test.orangeocssample.data.interactor

import com.test.orangeocssample.domaine.Schedule
import com.test.orangeocssample.domaine.SearchScheduleRepository
import io.reactivex.Single

class SearchScheduleInteractor(private val repository: SearchScheduleRepository) {
    fun getSchedule(query: String, offset: Int): Single<List<Schedule>> {
        return repository.getSchedulesStreamBy(query, offset)
    }
}