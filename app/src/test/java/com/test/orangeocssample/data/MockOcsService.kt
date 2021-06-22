package com.test.orangeocssample.data

import com.test.orangeocssample.data.api.OcsService
import com.test.orangeocssample.data.api.ScheduleSearchResponse

class MockOcsService : OcsService{
    override suspend fun searchSchedules(
        title: String,
        offset: Int,
        limit: Int
    ) =
        ScheduleSearchResponse(listOf(ScheduleFactory().createSchedule()))

}