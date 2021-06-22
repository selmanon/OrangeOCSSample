package com.test.orangeocssample.domaine

import io.reactivex.Single

interface OcsRepository {
    fun getSchedulesStreamBy(title: String, offset: Int): Single<Schedule>
}