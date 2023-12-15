package com.tc.gamegallery.domain

data class GameCatalog(
    val nextPage: Int? = null,
    val results: List<ResultGames> = emptyList()
)
