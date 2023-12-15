package com.tc.gamegallery.data

import com.tc.gamegallery.GameCatalogQuery
import com.tc.gamegallery.domain.ResultGames

fun GameCatalogQuery.Result.toResult(): ResultGames {
    return ResultGames(
        id = id,
        name = name ?: "No Name",
        //backgroundImage = backgroundImage ?: "No background image",
        thumbnailImage = thumbnailImage ?: "No thumbnailImage",
        tags = tags?.mapNotNull { it.toTag() } ?: emptyList(),
        genres = genres?.mapNotNull { it.toGenre() } ?: emptyList()
    )
}