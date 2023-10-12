package com.tc.gamegallery.data

import com.tc.gamegallery.GameCatalogQuery
import com.tc.gamegallery.domain.GameCatalog
import com.tc.gamegallery.domain.Tag

fun GameCatalogQuery.Result.toGameCatalog(): GameCatalog {
    return GameCatalog(
        id = id,
        name = name ?: "No Name",
        backgroundImage = backgroundImage ?: "No background image",
        tags = tags?.mapNotNull { it.toTag() } ?: emptyList()
    )
}