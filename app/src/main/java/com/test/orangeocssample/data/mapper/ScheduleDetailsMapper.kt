package com.test.orangeocssample.data.mapper

import com.test.orangeocssample.data.api.dto.details.ScheduleDetailsResponse
import com.test.orangeocssample.domaine.ScheduleDetails

class ScheduleDetailsMapper {
    fun mapToDomain(
        scheduleDetailsResponse: ScheduleDetailsResponse?,
    ) = ScheduleDetails(scheduleDetailsResponse?.contents?.pitch ?: "")

}
