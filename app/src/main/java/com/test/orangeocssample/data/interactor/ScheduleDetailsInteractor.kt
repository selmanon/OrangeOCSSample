package com.test.orangeocssample.data.interactor

import com.test.orangeocssample.domaine.ScheduleDetails
import com.test.orangeocssample.domaine.ScheduleDetailsRepository
import io.reactivex.Single

class ScheduleDetailsInteractor(private val repository: ScheduleDetailsRepository) {
    fun getScheduleDetails(detailLink: String): Single<ScheduleDetails> {
        return repository.getScheduleDetails(detailLink)
    }
}