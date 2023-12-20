package com.tc.gamegallery.domain

data class ResultGameSeries(
    val id : Int = 0,
    val name: String = "",
    val thumbnailImage: String = "",
    val genres: List<Genre> = emptyList(),
    val platforms: List<Platform> = emptyList(),
)
