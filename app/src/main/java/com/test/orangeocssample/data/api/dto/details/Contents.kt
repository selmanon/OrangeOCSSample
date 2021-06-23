package com.test.orangeocssample.data.api.dto.details

data class Contents(
    val acontents: List<Acontent>,
    val availability: List<Availability>,
    val bannerinfo: List<Bannerinfo>,
    val description: List<List<String>>,
    val duration: String,
    val fullscreenimageurl: String,
    val highlefticon: List<String>,
    val highrighticon: Any,
    val id: String,
    val imageurl: String,
    val isbookmarkable: Boolean,
    val isdownloadable: Boolean,
    val linearplanning: Any,
    val number: Any,
    val parentalrating: Int,
    val pitch: String,
    val playinfo: PlayinfoX,
    val playinfoid: PlayinfoidX,
    val subtitle: Any,
    val subtitlefocus: Any,
    val summary: String,
    val title: List<TitleX>,
    val zonesinfo: Zonesinfo,
    val seasons : List<Season>
)