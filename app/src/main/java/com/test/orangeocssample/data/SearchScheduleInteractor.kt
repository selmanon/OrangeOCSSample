package com.test.orangeocssample.data

import com.test.orangeocssample.domaine.OcsRepository
import com.test.orangeocssample.domaine.Schedule
import io.reactivex.Single

class SearchScheduleInteractor(private val repository: OcsRepository) {
    fun getSchedule(query: String, offset: Int): Single<List<Schedule>> {
        return repository.getSchedulesStreamBy(query, offset)
    }
}