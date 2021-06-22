package com.test.orangeocssample.data.api

data class ScheduleResponse(
    val detaillink: String? = null,
    val duration: String? = null,
    val fullscreenimageurl: String? = null,
    val highlefticon: Any? = null,
    val highrighticon: Any? = null,
    val id: String? = null,
    val imageurl: String? = null,
    val lowrightinfo: Any? = null,
    val playinfoid: Playinfoid? = null,
    val subtitle: String? = null,
    val subtitlefocus: Any? = null,
    val title: List<Title>?
)