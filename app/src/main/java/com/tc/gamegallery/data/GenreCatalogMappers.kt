package com.tc.gamegallery.data

import com.tc.gamegallery.GameCatalogQuery
import com.tc.gamegallery.domain.Genre

fun GameCatalogQuery.Genre.toGenre(): Genre {
    return Genre(
        name = name ?: "no genre"
    )
}