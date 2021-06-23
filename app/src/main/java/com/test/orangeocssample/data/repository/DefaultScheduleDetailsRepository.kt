package com.test.orangeocssample.data.repository

import com.test.orangeocssample.data.mapper.ScheduleDetailsMapper
import com.test.orangeocssample.data.api.OcsService
import com.test.orangeocssample.domaine.ScheduleDetails
import com.test.orangeocssample.domaine.ScheduleDetailsRepository
import io.reactivex.Single

class DefaultScheduleDetailsRepository(
    private val ocsService: OcsService,
    private val mapper: ScheduleDetailsMapper
) :
    ScheduleDetailsRepository {

    override fun getScheduleDetails(detailLink: String): Single<ScheduleDetails> {
        return ocsService.scheduleDetails(detailLink)
            .map {
                mapper.mapToDomain(it)
            }
    }
}