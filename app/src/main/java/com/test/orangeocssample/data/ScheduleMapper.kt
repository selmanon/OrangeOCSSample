package com.test.orangeocssample.data

import com.test.orangeocssample.data.api.ScheduleResponse
import com.test.orangeocssample.domaine.Schedule

class ScheduleMapper {
    fun mapToDomain(scheduleResponse: ScheduleResponse) =
        Schedule(
            id = scheduleResponse.id ?: "",
            title = scheduleResponse.title!![0].value ?: "",
            subtitle = scheduleResponse.subtitle ?: "",
            imageUrl = scheduleResponse.imageurl ?: ""
        )
}
