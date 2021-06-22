package com.test.orangeocssample.data

import com.test.orangeocssample.data.api.ScheduleResponse
import com.test.orangeocssample.domaine.Schedule
import okhttp3.internal.toImmutableList

class ScheduleMapper {
    fun mapToDomain(scheduleResponse: List<ScheduleResponse>?): List<Schedule> {
        val schedulesDomain: MutableList<Schedule> = mutableListOf()
        scheduleResponse?.forEach {
            schedulesDomain.add(
                Schedule(
                    id = it.id ?: "",
                    title = it.title!![0].value ?: "",
                    subtitle = it.subtitle ?: "",
                    imageUrl = it.imageurl ?: ""
                )
            )
        }
        return schedulesDomain.toImmutableList()
    }

}
