package com.test.orangeocssample.domaine

import io.reactivex.Single

interface ScheduleDetailsRepository {
    fun getScheduleDetails(detailLink: String): Single<ScheduleDetails>
}