package com.tc.gamegallery.domain

data class GameSeries(
    val nextPage: Int? = null,
    val results: List<ResultGameSeries> = emptyList()
)
