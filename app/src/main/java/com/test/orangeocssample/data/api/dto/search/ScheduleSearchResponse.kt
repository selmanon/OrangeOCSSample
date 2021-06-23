package com.test.orangeocssample.data.api.dto.search

import com.google.gson.annotations.SerializedName

data class ScheduleSearchResponse<T>(
    @SerializedName("contents") val results: List<T>?
)