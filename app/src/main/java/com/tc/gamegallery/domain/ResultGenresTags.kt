package com.tc.gamegallery.domain

data class ResultGenresTags(
    val id: Int,
    val name: String,
    val thumbnailImage: String,
    val games: List<GameGenresTags>
)
