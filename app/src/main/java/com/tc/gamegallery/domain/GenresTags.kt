package com.tc.gamegallery.domain

data class GenresTags(
    val nextPage: Int? = null,
    val results: List<ResultGenresTags> = emptyList()
)
