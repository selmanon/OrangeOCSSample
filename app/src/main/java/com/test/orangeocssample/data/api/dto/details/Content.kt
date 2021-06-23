package com.test.orangeocssample.data.api.dto.details

data class Content(
    val detaillink: String,
    val duration: String,
    val fullscreenimageurl: String,
    val highlefticon: Any,
    val highrighticon: Any,
    val id: String,
    val imageurl: String,
    val lowrightinfo: Any,
    val playinfo: Playinfo,
    val playinfoid: Playinfoid,
    val subtitle: String,
    val subtitlefocus: Any,
    val title: List<Title>
)