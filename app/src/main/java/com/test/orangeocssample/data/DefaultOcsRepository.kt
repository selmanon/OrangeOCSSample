package com.test.orangeocssample.data

import com.test.orangeocssample.data.api.OcsService
import com.test.orangeocssample.data.api.ScheduleResponse
import com.test.orangeocssample.data.api.ScheduleSearchResponse
import com.test.orangeocssample.data.api.TITLE_QUALIFIER
import com.test.orangeocssample.domaine.OcsRepository
import com.test.orangeocssample.domaine.Schedule
import io.reactivex.Single

class DefaultOcsRepository(private val ocsService: OcsService, private val mapper: ScheduleMapper) :
    OcsRepository {

    override fun getSchedulesStreamBy(title: String, offset: Int): Single<List<Schedule>> {
        return ocsService.searchSchedules(TITLE_QUALIFIER + title, offset)
            .map { response: ScheduleSearchResponse<ScheduleResponse> ->
                mapper.mapToDomain(response.results)
            }
    }
}