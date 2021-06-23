package com.test.orangeocssample.data

import com.test.orangeocssample.data.api.dto.search.ScheduleResponse
import com.test.orangeocssample.data.api.dto.search.Title

class ScheduleFactory {

    companion object {
        private val SCHEDULE_CONTENT = ScheduleResponse(
            detaillink = null,
            duration = null,
            fullscreenimageurl = "",
            highlefticon = null,
            highrighticon = null,
            id = "BOGOT7SANSAW0125563",
            imageurl = "/data_plateforme/program/31412/origin_bogot7sansaw0125563_z8p34.jpg?size=small",
            lowrightinfo = null,
            playinfoid = null,
            subtitle = "saisons 1 à 8",
            subtitlefocus = null,
            title = listOf(Title(null, null, "GAME OF THRONES"))
        )
    }

    fun createSchedule(): ScheduleResponse {
        return SCHEDULE_CONTENT
    }
}
