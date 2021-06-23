package com.test.orangeocssample.data.api.dto.details

data class Acontent(
    val contents: List<Content>,
    val description: List<Description>,
    val imageurl: String,
    val link: String,
    val type: String
)