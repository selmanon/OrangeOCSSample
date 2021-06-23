package com.test.orangeocssample.data.repository

import com.test.orangeocssample.data.mapper.ScheduleMapper
import com.test.orangeocssample.data.api.OcsService
import com.test.orangeocssample.data.api.TITLE_QUALIFIER
import com.test.orangeocssample.domaine.SearchScheduleRepository
import com.test.orangeocssample.domaine.Schedule
import io.reactivex.Single

class DefaultSearchScheduleRepository(private val ocsService: OcsService, private val mapper: ScheduleMapper) :
    SearchScheduleRepository {

    override fun getSchedulesStreamBy(title: String, offset: Int): Single<List<Schedule>> {
        return ocsService.searchSchedules(TITLE_QUALIFIER + title, offset)
            .map {
                    mapper.mapToDomain(it.results)
            }
    }
}