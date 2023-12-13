package com.tc.gamegallery.domain

data class GameDetails(
    val name: String = "No",
    val description: String = "No",
    val metacritic: Int = 0,
    val genres: List<Genre> = listOf(),
    val esrbRating: EsrbRating = EsrbRating(),
    val publishers: List<GamePublisher> = listOf(),
    val thumbnailImage: String = "NA",
    val backgroundImage: String = "NA",
    val screenshots: List<Screenshot> = listOf()
)
