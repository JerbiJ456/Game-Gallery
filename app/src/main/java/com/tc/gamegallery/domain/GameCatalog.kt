package com.tc.gamegallery.domain

data class GameCatalog(
    val id : Int,
    val name: String,
    val backgroundImage: String,
    val tags : List<Tag>
)