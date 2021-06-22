package com.test.orangeocssample.data

import com.test.orangeocssample.data.api.OcsService
import com.test.orangeocssample.data.api.ScheduleResponse
import com.test.orangeocssample.data.api.ScheduleSearchResponse
import com.test.orangeocssample.domaine.OcsRepository
import com.test.orangeocssample.domaine.Schedule
import io.reactivex.Single

class DefaultOcsRepository(private val ocsService: OcsService, private val mapper: ScheduleMapper) :
    OcsRepository {
    override fun getSchedulesStreamBy(title: String, offset: Int): Single<Schedule> {
        return ocsService.searchSchedules(title, offset)
            .map { response: ScheduleSearchResponse<ScheduleResponse> ->
                mapper.mapToDomain(response.results!![0])
            }
    }
}