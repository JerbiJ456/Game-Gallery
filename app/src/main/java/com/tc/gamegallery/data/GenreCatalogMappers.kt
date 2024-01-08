package com.tc.gamegallery.data

import com.tc.gamegallery.GameCatalogQuery
import com.tc.gamegallery.GameFromIdQuery
import com.tc.gamegallery.domain.Genre

fun GameCatalogQuery.Genre.toGenre(): Genre {
    return Genre(
        name = name ?: "no genre"
    )
}

fun GameFromIdQuery.Genre.toGenre(): Genre {
    return Genre(
        name = name ?: "no genre"
    )
}