package com.tc.gamegallery.domain

data class ResultGames(
    val id : Int = 0,
    val name: String = "",
    val thumbnailImage: String = "",
    val tags : List<Tag> = emptyList(),
    val genres: List<Genre> = emptyList(),
    val platforms: List<Platform>,
)
