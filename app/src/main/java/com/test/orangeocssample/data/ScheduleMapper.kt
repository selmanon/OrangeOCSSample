package com.test.orangeocssample.data

import com.test.orangeocssample.data.api.dto.details.ScheduleDetails
import com.test.orangeocssample.data.api.dto.search.ScheduleResponse
import com.test.orangeocssample.domaine.Schedule
import okhttp3.internal.toImmutableList

class ScheduleMapper {
    fun mapToDomain(
        scheduleResponse: List<ScheduleResponse>?,
        scheduleDetails: ScheduleDetails
    ): List<Schedule> {
        val schedulesDomain: MutableList<Schedule> = mutableListOf()
        scheduleResponse?.forEach {
            schedulesDomain.add(
                Schedule(
                    id = it.id ?: "",
                    title = it.title!![0].value ?: "",
                    subtitle = it.subtitle ?: "",
                    imageUrl = it.imageurl ?: "",
                    pitch = scheduleDetails.contents.pitch
                )
            )
        }
        return schedulesDomain.toImmutableList()
    }

}
