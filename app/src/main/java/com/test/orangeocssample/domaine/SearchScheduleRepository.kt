package com.test.orangeocssample.domaine

import io.reactivex.Single

interface SearchScheduleRepository {
    fun getSchedulesStreamBy(title: String, offset: Int): Single<List<Schedule>>
}