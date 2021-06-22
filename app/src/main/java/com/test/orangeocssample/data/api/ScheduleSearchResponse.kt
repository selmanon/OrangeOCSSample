package com.test.orangeocssample.data.api

import com.google.gson.annotations.SerializedName

data class ScheduleSearchResponse<T>(
    @SerializedName("contents") val results: List<T>?
)